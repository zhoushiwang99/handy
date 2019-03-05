package com.finals.handy.service;

import com.auth0.jwt.interfaces.Claim;
import com.finals.handy.bean.ExpressOrder;
import com.finals.handy.constant.Constant;
import com.finals.handy.constant.ResponseCode;
import com.finals.handy.mapper.ExpressOrderMapper;
import com.finals.handy.util.GenerateNumUtil;
import com.finals.handy.util.JwtUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zsw
 */
@Service
public class ExpressOrderService {

    @Autowired
    ExpressOrderMapper expressOrderMapper;

    @Autowired
    GenerateNumUtil generateNumUtil;

    @Autowired
    MessageService messageService;

    @Autowired
    UserOrderService userOrderService;

    @Autowired
    RedisService redisService;


    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> finishOrder(String accessToken,String expressOrderNum,String comment,Integer score){

        Map<String,Object> map = new HashMap<>(16);
        List<Integer> scores = Arrays.asList(1,2,3,4,5);
        if(!scores.contains(score)){
            map.put("code",ResponseCode.PARAM_ILLEGAL.getValue());
            return map;
        }
        if(expressOrderMapper.isOrderExistByOrderNum(expressOrderNum) != 1){
            map.put("code",ResponseCode.ORDER_NOT_EXIST.getValue());
            return map;
        }

        int publisherId = expressOrderMapper.getPublisherIdByOrderNum(expressOrderNum);

        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);

        int userId = Integer.parseInt(claimMap.get("userId").asString());

        if(publisherId != userId){
            map.put("code",ResponseCode.ILLEGAL_REQUEST.getValue());
            map.put("msg","并非订单发布者");
            return map;
        }

        if(expressOrderMapper.isOrderHasFinished(expressOrderNum) != 0){
            map.put("code",ResponseCode.ORDER_HAS_FINISHED.getValue());
            map.put("msg","订单已完成");
            return map;
        }


        if(!expressOrderMapper.isOrderReceived(expressOrderNum)){
            map.put("code",ResponseCode.ILLEGAL_REQUEST.getValue());
            map.put("msg","订单尚未被接取，无法确认订单完成");
            return map;
        }

        expressOrderMapper.finishOrder(expressOrderNum);
        int receiverId = Integer.parseInt(expressOrderMapper.getReceiverIdByOrderNum(expressOrderNum));
        expressOrderMapper.deleteOrderForPublicAfterFinish(expressOrderNum);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        System.out.println(time);

        expressOrderMapper.addCommentAndTimeAndScore(expressOrderNum,comment,time,score);

        //为发布者和接取者增加订单数量
        userOrderService.addReceiveOrderNumForUser(String.valueOf(receiverId),1,score);
        userOrderService.addPublishOrderNumForUser(String.valueOf(userId),1);


        String text = "您好，您接取的订单:" + expressOrderNum + "已完成,感谢您的使用，谢谢";

        messageService.SendMessage(Constant.HANDY_OFFICER_ID,receiverId,text);

        map.put("code",ResponseCode.REQUEST_SUCCEED.getValue());

        return map;

    }

    /**
     *
     * 订单接取者同意删除订单
     * @param accessToken
     * @param expressOrderNum
     * @return
     */
    public Map<String,Object> agreeDeleteOrder(String accessToken,@Size String expressOrderNum) {
        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);
        String userId = claimMap.get("userId").asString();

        Map<String,Object> map = new HashMap<>(16);


        System.out.println(expressOrderMapper.isOrderExistByOrderNum(expressOrderNum));
        if(expressOrderMapper.isOrderExistByOrderNum(expressOrderNum) != 1){
            map.put("code",ResponseCode.ORDER_NOT_EXIST.getValue());
            return map;
        }

        String receiverId = expressOrderMapper.getReceiverIdByOrderNum(expressOrderNum);
        //是否为当前订单接取者
        if(!receiverId.equals(userId)){
            map.put("code",ResponseCode.NOT_ORDER_RECEIVER.getValue());
            return map;
        }

        if(!expressOrderMapper.isWantDeleteByOrderNum(expressOrderNum)){
            map.put("code",ResponseCode.ILLEGAL_REQUEST.getValue());
            return map;
        }else{
            expressOrderMapper.deleteExpressOrderByOrderNum(expressOrderNum);
            map.put("code",ResponseCode.REQUEST_SUCCEED.getValue());
            return map;
        }
    }



    /**
     * 删除订单调用
     *
     * @param accessToken
     * @param orderNum1
     * @return
     */
    public Map<String, Object> deleteExpressOrder(String accessToken, String orderNum1) {


        String expressOrderId = expressOrderMapper.getOrderIdByOrderNum(orderNum1);

        Map<String, Object> map = new HashMap<>(16);

        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);
        int userId = Integer.parseInt(claimMap.get("userId").asString());
        if (expressOrderMapper.isOrderExistByOrderId(expressOrderId) != 1) {
            map.put("code", ResponseCode.ORDER_NOT_EXIST.getValue());
            return map;
        }

        int publisherId = expressOrderMapper.getPublisherIdByOrderId(expressOrderId);

        if(publisherId != userId){
            map.put("code",ResponseCode.ILLEGAL_REQUEST.getValue());
            map.put("msg","并非订单发布者");
            return map;
        }

        if (!expressOrderMapper.isReceived(expressOrderId)) {
            expressOrderMapper.deleteExpressOrder(expressOrderId);
            map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
            return map;
        } else {
            String orderNum = expressOrderMapper.getOrderNumByOrderId(expressOrderId);
            String receiverId = expressOrderMapper.getReceiverIdByOrderId(expressOrderId);
            String text = "用户您好，您接取的订单:" + orderNum + "的发布者请求取消订单，现征求您的意见，请问您是否同意取消订单";

            expressOrderMapper.wantToDeleteByOrderNum(orderNum);
            messageService.SendMessage(11, Integer.valueOf(receiverId),text);
            map.put("code", ResponseCode.ORDER_DELETE_PROCESSING.getValue());
            return map;
        }
    }


    public Map<String, Object> getPublishedOrder(int pageNo, int pageSize) {
        String orderBy = "id asc";
        Map<String, Object> map = new HashMap<>(16);
        PageHelper.startPage(pageNo, pageSize, orderBy);
        Page<ExpressOrder> expressOrders = expressOrderMapper.getPublishedExpressOrder();

        PageInfo<ExpressOrder> pageInfo = new PageInfo<>(expressOrders);
        map.put("expressOrder", pageInfo);
        map.put("code", "0");
        return map;
    }


    /**
     * 发布代领快递订单
     *
     * @param expressOrder
     * @return
     */
    public Map<String, Object> releaseFoodOrder(String accessToken, ExpressOrder expressOrder) {
        Map<String, Object> map = new HashMap<>(16);
        String[] strings = new String[]{"东门", "西门", "南门", "弘二"};

        boolean isSame = false;
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].equals(expressOrder.getPickupDoor())) {
                isSame = true;
                break;
            }
        }
        if (!isSame) {
            map.put("code", ResponseCode.PARAM_ILLEGAL.getValue());
            return map;
        }


        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);

        int publisherId = Integer.parseInt(claimMap.get("userId").asString());

        expressOrder.setPublisherId(publisherId);

        expressOrder.setOrderNumber(generateNumUtil.getExpressOrderNumber());

        expressOrderMapper.realseExpressOrder(expressOrder);
        map.put("orderNum", expressOrder.getOrderNumber());
        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
        return map;
    }

    public Map<String, Object> receiveExpressOrder(String accessToken, String orderNum) {

        Map<String, Object> map = new HashMap<>(16);
        //订单是否存在
        if (expressOrderMapper.isOrderExistByOrderNum(orderNum) != 1) {
            map.put("code", ResponseCode.ORDER_NOT_EXIST.getValue());
            return map;
        }

        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);
        int userId = Integer.parseInt(claimMap.get("userId").asString());
        int publisherId = expressOrderMapper.getPublisherIdByOrderNum(orderNum);
        //订单发布者和接取者是否为同一个人
        if (userId == publisherId) {
            map.put("code", ResponseCode.ORDER_PUBLISH_BY_ONE.getValue());
            return map;
        }

        boolean isReceived = expressOrderMapper.isOrderReceived(orderNum);

        if (isReceived) {
            map.put("code", ResponseCode.ORDER_HAS_RECEIVED.getValue());
            return map;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String time = sdf.format(new Date());

        expressOrderMapper.receiveExpressOrder(orderNum, userId, time);


        String text = "您发布的订单:" + orderNum + "已被我接取,如有特殊情况请尽快与我联系";

        messageService.SendMessage(userId,publisherId,text);

        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());

        return map;
    }

}
