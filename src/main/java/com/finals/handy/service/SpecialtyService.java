package com.finals.handy.service;

import com.auth0.jwt.interfaces.Claim;
import com.finals.handy.bean.SpecialtyOrder;
import com.finals.handy.constant.Constant;
import com.finals.handy.constant.ResponseCode;
import com.finals.handy.mapper.SpecialtyMapper;
import com.finals.handy.mapper.UserOrderMapper;
import com.finals.handy.util.GenerateNumUtil;
import com.finals.handy.util.JwtUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zsw
 */
@Service
public class SpecialtyService {

    @Autowired
    SpecialtyMapper specialtyMapper;

    @Autowired
    GenerateNumUtil generateNumUtil;

    @Autowired
    UserOrderMapper userOrderMapper;

    @Autowired
    UserOrderService userOrderService;


    @Autowired
    MessageService messageService;

    @Autowired
    RedisService redisService;

    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> finishOrder(String accessToken, @Size(min = 0, max = 40) String comment, String orderNum,Integer score) {
        Map<String, Object> map = new HashMap<>(16);

        List<Integer> scores = Arrays.asList(1,2,3,4,5);
        if(!scores.contains(score)){
            map.put("code",ResponseCode.PARAM_ILLEGAL.getValue());
            map.put("msg","评分只能为1、2、3、4、5");
            return map;
        }


        if (specialtyMapper.isOrderExistByOrderNum(orderNum) != 1) {
            map.put("code", ResponseCode.ORDER_NOT_EXIST.getValue());
            map.put("msg", "订单不存在");
            return map;
        }

        String publisherId = specialtyMapper.getPublisherIdByOrderNum(orderNum);

        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);

        String userId = claimMap.get("userId").asString();

        if (!userId.equals(publisherId)) {
            map.put("code", ResponseCode.ILLEGAL_REQUEST.getValue());
            map.put("msg", "不是此订单的发布者");
            return map;
        }
        if (specialtyMapper.isOrderHasFinishedByOrderNum(orderNum) > 0) {
            map.put("code", ResponseCode.ORDER_HAS_FINISHED.getValue());
            map.put("msg", "订单已完成");
            return map;
        }
        if (!specialtyMapper.isOrderReceivedByOrderNum(orderNum)) {
            map.put("code", ResponseCode.ILLEGAL_REQUEST.getValue());
            map.put("msg", "订单未被接取，无法完成");
            return map;
        }
        specialtyMapper.finishOrderByOrderNum(orderNum);
        String receivedId = specialtyMapper.getReceivedIdByOrderNum(orderNum);

        specialtyMapper.deleteOrderFromPublishedAfterFinishByOrderNum(orderNum);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        specialtyMapper.addCommentAndTimeAndScoreByOrderNum(orderNum, comment, time,score);

        userOrderMapper.deletePublishOrder(orderNum);
        userOrderMapper.deleteReceiveOrder(orderNum);
        userOrderMapper.addFinishedOrder(orderNum,publisherId,receivedId);

        String text = "您好，您接取的订单:" + orderNum + "已完成,感谢您的使用，谢谢";

       //为发布者和接取着增加订单数量
        userOrderService.addPublishOrderNumForUser(userId,1);
        userOrderService.addReceiveOrderNumForUser(receivedId,1,score);

        messageService.SendMessage(Constant.HANDY_OFFICER_ID, Integer.valueOf(receivedId), text);
        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
        return map;

    }

    /**
     * 订单接取者同意删除订单
     *
     * @param accessToken
     * @param orderNum
     * @return
     */
    public Map<String, Object> agreeDelete(String accessToken, String orderNum) {
        Map<String, Object> map = new HashMap<>(16);
        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);
        String userId = claimMap.get("userId").asString();

        if (specialtyMapper.isOrderExistByOrderNum(orderNum) != 1) {
            map.put("code", ResponseCode.ORDER_NOT_EXIST.getValue());
            map.put("msg", "订单不存在");
            return map;
        }

        String receiverId = specialtyMapper.getReceivedIdByOrderNum(orderNum);

        if (!receiverId.equals(userId)) {
            map.put("code", ResponseCode.NOT_ORDER_RECEIVER.getValue());
            map.put("msg", "不是订单接取者");
            return map;
        }

        if (!specialtyMapper.isPublisherWantDeleteByOrderNum(orderNum)) {
            map.put("code", ResponseCode.ILLEGAL_REQUEST.getValue());
            map.put("msg", "发布者未同意删除订单");
            return map;
        } else {
            specialtyMapper.deleteOrderByOrderNum(orderNum);
            userOrderMapper.deletePublishOrder(orderNum);
            userOrderMapper.deleteReceiveOrder(orderNum);
            map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
            return map;
        }

    }

    /**
     * 删除自己发布的的订单
     *
     * @param accessToken
     * @param orderNum
     * @return
     */
    public Map<String, Object> deleteMyOrder(String accessToken, String orderNum) {
        Map<String, Object> map = new HashMap<>(16);
        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);
        String userId = claimMap.get("userId").asString();

        if (specialtyMapper.isOrderExistByOrderNum(orderNum) != 1) {
            map.put("code", ResponseCode.ORDER_NOT_EXIST.getValue());
            map.put("msg", "订单不存在");
            return map;
        }

        String publisherId = specialtyMapper.getPublisherIdByOrderNum(orderNum);

        if (!publisherId.equals(userId)) {
            map.put("code", ResponseCode.ILLEGAL_REQUEST.getValue());
            map.put("msg", "并非订单发布者");
            return map;
        }


        if (!specialtyMapper.isOrderReceivedByOrderNum(orderNum)) {
            specialtyMapper.deleteOrderByOrderNum(orderNum);
            userOrderMapper.deletePublishOrder(orderNum);
            map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
            return map;
        } else {
            String receiverId = specialtyMapper.getReceivedIdByOrderNum(orderNum);
            String text = "用户您好，您接取的订单:" + orderNum + "的发布者请求取消订单，现征求您的意见，请问您是否同意取消订单";
            specialtyMapper.publisherWantDeleteByOrderNum(orderNum);
            messageService.SendMessage(Constant.HANDY_OFFICER_ID, Integer.valueOf(receiverId), text);
            map.put("code", ResponseCode.ORDER_DELETE_PROCESSING.getValue());
            return map;
        }

    }


    /**
     * 列出当前可被接取的订单
     *
     * @return
     */
    public Map<String, Object> listSpecialtyOrder(int pageNo, int pageSize) {
        String orderBy = "id asc";
        Map<String, Object> map = new HashMap<>(16);
        PageHelper.startPage(pageNo, pageSize, orderBy);

        Page<SpecialtyOrder> order = specialtyMapper.getPublishSpecialtyOrder();
        PageInfo<SpecialtyOrder> orders = new PageInfo<>(order);
        map.put("orders", orders);
        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
        return map;
    }


    /**
     * 发布代购特产订单
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> publishOrder(String accessToken, SpecialtyOrder specialtyOrder) {
        Map<String, Object> map = new HashMap<>(16);

        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);

        String userId = claimMap.get("userId").asString();

        //设置订单发布时间及订单号
        specialtyOrder.setPublisherId(Integer.valueOf(userId));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        specialtyOrder.setPublishTime(sdf.format(new Date()));
        specialtyOrder.setOrderNum(generateNumUtil.getSpecialtyOrderNumber());

        specialtyOrder.setAllMoney(specialtyOrder.getPayMoney() + specialtyOrder.getMoney());


        //将订单信息添加到数据库
        specialtyMapper.publishOrder(specialtyOrder);
        userOrderMapper.addPublishOrder(userId,specialtyOrder.getOrderNum());


        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
        map.put("orderNum",specialtyOrder.getOrderNum());
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> receiveOrder(String accessToken, String orderNum) {
        Map<String, Object> map = new HashMap<>(16);

        if (specialtyMapper.isOrderExistByOrderNum(orderNum) != 1) {
            map.put("code", ResponseCode.ORDER_NOT_EXIST.getValue());
            map.put("msg", "订单不存在");
            return map;
        }

        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);
        String userId = claimMap.get("userId").asString();
        String publisherId = specialtyMapper.getPublisherIdByOrderNum(orderNum);
        //订单发布者和接取者是否为同一个人
        if (userId.equals(publisherId)) {
            map.put("code", ResponseCode.ORDER_PUBLISH_BY_ONE.getValue());
            map.put("msg", "不能接取自己发布的订单");
            return map;
        }

        boolean isReceived = specialtyMapper.isOrderReceivedByOrderNum(orderNum);

        if (isReceived) {
            map.put("code", ResponseCode.ORDER_HAS_RECEIVED.getValue());
            map.put("msg", "订单已被接取");
            return map;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String time = sdf.format(new Date());

        specialtyMapper.receiveOrderByOrderNum(orderNum, userId, time);
        userOrderMapper.addReceiveOrder(userId,orderNum);

        String text = "您发布的订单:" + orderNum + "已被我接取,如有特殊情况请尽快与我联系";

        messageService.SendMessage(Integer.parseInt(userId), Integer.parseInt(publisherId), text);

        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());

        return map;
    }

    /**
     * 获取该用户发布的但未完成的订单
     *
     * @param accessToken
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Map<String, Object> getMySpecialtyOrder(String accessToken, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "3") int pageSize) {
        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);
        String userId = claimMap.get("userId").asString();
        String orderBy = "id asc";
        PageHelper.startPage(pageNo, pageSize, orderBy);
        Page<SpecialtyOrder> orders = specialtyMapper.getMySpecialtyOrder(userId);
        PageInfo<SpecialtyOrder> myorders = new PageInfo<>(orders);
        Map<String, Object> map = new HashMap<>(16);
        map.put("orders", myorders);
        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
        return map;
    }


}


