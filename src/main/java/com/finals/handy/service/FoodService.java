package com.finals.handy.service;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.Claim;
import com.finals.handy.bean.Food;
import com.finals.handy.bean.FoodOrder;
import com.finals.handy.constant.Constant;
import com.finals.handy.constant.ResponseCode;
import com.finals.handy.mapper.FoodOrderMapper;
import com.finals.handy.mapper.UserOrderMapper;
import com.finals.handy.util.GenerateNumUtil;
import com.finals.handy.util.JwtUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zsw
 */
@Service
public class FoodService {

    @Autowired
    GenerateNumUtil generateNumUtil;

    @Autowired
    FoodOrderMapper foodOrderMapper;

    @Autowired
    MessageService messageService;

    @Autowired
    UserOrderMapper userOrderMapper;


    @Autowired
    UserOrderService userOrderService;

    private static final int MAX_FOOD_SIZE = 10;

    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> publishOrder(String accessToken, FoodOrder foodOrder, String foodList) {
        Map<String, Object> map = new HashMap<>(16);

        try {
            List<Food> foods = JSON.parseArray(foodList, Food.class);
            if (foods.size() > MAX_FOOD_SIZE) {
                map.put("code", ResponseCode.PARAM_ILLEGAL.getValue());
                map.put("msg", "食物数量超出10个");
                return map;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            foodOrder.setPublishTime(sdf.format(new Date()));
            Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);
            String userId = claimMap.get("userId").asString();
            foodOrder.setPublisherId(userId);
            double totalMoney = 0;
            for (int i = 0; i < foods.size(); i++) {
                totalMoney += foods.get(i).getMoney();
            }
            foodOrder.setTotalMoney(totalMoney);
            foodOrder.setAllMoney(totalMoney + foodOrder.getPayMoney());
            System.out.println("totalMoney:" + foodOrder.getTotalMoney());
            foodOrder.setFoods(foods);
            foodOrder.setOrderNumber(generateNumUtil.getFoodOrderNumber());

            foodOrderMapper.publishOrder(foodOrder);
            foodOrderMapper.addFoodToPublish(foodOrder.getOrderNumber(), foods);

            userOrderMapper.addPublishOrder(userId,foodOrder.getOrderNumber());

            map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
            map.put("orderNum", foodOrder.getOrderNumber());
            return map;
        } catch (Exception e) {
            map.put("code", ResponseCode.PARAM_ILLEGAL.getValue());
            map.put("msg", "foods参数非法");
            return map;
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> receiveOrder(String accessToken, String orderNum) {

        Map<String, Object> map = new HashMap<>(16);
        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);
        String userId = claimMap.get("userId").asString();
        if (foodOrderMapper.isOrderExistByOrderNum(orderNum) != 1) {
            map.put("code", ResponseCode.ORDER_NOT_EXIST.getValue());
            return map;
        }

        String publisherId = foodOrderMapper.getPublisherIdByOrderNum(orderNum);

        if (publisherId.equals(userId)) {
            map.put("code", ResponseCode.ILLEGAL_REQUEST.getValue());
            map.put("msg", "不能接取自己发布的订单");
            return map;
        }
        if (foodOrderMapper.isOrderHasReceivedByOrderNum(orderNum)) {
            map.put("code", ResponseCode.ILLEGAL_REQUEST.getValue());
            map.put("msg", "订单已被接取");
            return map;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String time = sdf.format(new Date());
        foodOrderMapper.receiveOrder(userId, orderNum, time);
        userOrderMapper.addReceiveOrder(userId,orderNum);


        String text = "您发布的订单:" + orderNum + "已被我接取,如有特殊情况请尽快与我联系";

        messageService.SendMessage(Integer.parseInt(userId), Integer.parseInt(publisherId), text);

        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());

        return map;

    }


    public Map<String, Object> getMyPublishFoodOrder(String accessToken, int pageNo, int pageSize) {

        Map<String, Object> map = new HashMap<>(16);

        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);
        String userId = claimMap.get("userId").asString();
        String orderBy = "id asc";
        PageHelper.startPage(pageNo, pageSize, orderBy);
        Page<FoodOrder> myPublishFoodOrder = foodOrderMapper.getMyPublishFoodOrder(userId);

        for (FoodOrder foodOrder : myPublishFoodOrder) {
            List<Food> foods = foodOrderMapper.getFoodsByOrderNum(foodOrder.getOrderNumber());
            foodOrder.setFoods(foods);
        }

        PageInfo<FoodOrder> myOrders = new PageInfo<>(myPublishFoodOrder);
        System.out.println(myOrders);

        map.put("foodOrders", myOrders);
        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
        return map;
    }

    public Map<String, Object> listFoodOrders(int pageNo, int pageSize) {
        String orderBy = "id asc";
        Map<String, Object> map = new HashMap<>(16);
        PageHelper.startPage(pageNo, pageSize, orderBy);
        Page<FoodOrder> ordersPage = foodOrderMapper.getPublishFoodOrder();
        for (FoodOrder foodOrder : ordersPage) {
            foodOrder.setFoods(foodOrderMapper.getFoodsByOrderNum(foodOrder.getOrderNumber()));
        }

        PageInfo<FoodOrder> orders = new PageInfo<>(ordersPage);
        map.put("orders", orders);
        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> agreeDeleteFoodOrder(String accessToken, String orderNum) {
        Map<String, Object> map = new HashMap<>(16);
        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);
        String userId = claimMap.get("userId").asString();

        if (foodOrderMapper.isOrderExistByOrderNum(orderNum) != 1) {
            map.put("code", ResponseCode.ORDER_NOT_EXIST.getValue());
            map.put("msg", "订单不存在");
            return map;
        }

        String receiverId = foodOrderMapper.getReceiverIdByOrderNum(orderNum);

        if (!receiverId.equals(userId)) {
            map.put("code", ResponseCode.NOT_ORDER_RECEIVER.getValue());
            map.put("msg", "不是订单接取者");
            return map;
        }

        if (!foodOrderMapper.isPublisherWantDeleteOrderByNum(orderNum)) {
            map.put("code", ResponseCode.ILLEGAL_REQUEST.getValue());
            map.put("msg", "发布者未同意删除订单");
            return map;
        } else {
            foodOrderMapper.deleteOrderByOrderNum(orderNum);
            userOrderMapper.deletePublishOrder(orderNum);
            userOrderMapper.deleteReceiveOrder(orderNum);
            map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
            return map;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> deleteFoodOrder(String accessToken, String orderNum) {
        Map<String, Object> map = new HashMap<>(16);
        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);
        String userId = claimMap.get("userId").asString();

        if (foodOrderMapper.isOrderExistByOrderNum(orderNum) != 1) {
            map.put("code", ResponseCode.ORDER_NOT_EXIST.getValue());
            map.put("msg", "订单不存在");
            return map;
        }
        String publisherId = foodOrderMapper.getPublisherIdByOrderNum(orderNum);

        if (!publisherId.equals(userId)) {
            map.put("code", ResponseCode.ILLEGAL_REQUEST.getValue());
            map.put("msg", "并非订单发布者");
            return map;
        }

        if (!foodOrderMapper.isOrderHasReceivedByOrderNum(orderNum)) {
            foodOrderMapper.deleteOrderByOrderNum(orderNum);
            userOrderMapper.deletePublishOrder(orderNum);
            map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
            return map;
        } else {
            String receiverId = foodOrderMapper.getReceiverIdByOrderNum(orderNum);
            String text = "用户您好，您接取的订单:" + orderNum + "的发布者请求取消订单，现征求您的意见，请问您是否同意取消订单";
            foodOrderMapper.wantDeleteByOrderNum(orderNum);
            messageService.SendMessage(Constant.HANDY_OFFICER_ID, Integer.valueOf(receiverId), text);
            map.put("code", ResponseCode.ORDER_DELETE_PROCESSING.getValue());
            return map;
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> finishOrder(String accessToken,String orderNum,int score,String comment){
        Map<String, Object> map = new HashMap<>(16);

        List<Integer> scores = Arrays.asList(1,2,3,4,5);
        if(!scores.contains(score)){
            map.put("code",ResponseCode.PARAM_ILLEGAL.getValue());
            map.put("msg","评分只能为1、2、3、4、5");
            return map;
        }

        if (foodOrderMapper.isOrderExistByOrderNum(orderNum) != 1) {
            map.put("code", ResponseCode.ORDER_NOT_EXIST.getValue());
            map.put("msg", "订单不存在");
            return map;
        }

        String publisherId = foodOrderMapper.getPublisherIdByOrderNum(orderNum);

        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);

        String userId = claimMap.get("userId").asString();

        if (!userId.equals(publisherId)) {
            map.put("code", ResponseCode.ILLEGAL_REQUEST.getValue());
            map.put("msg", "不是此订单的发布者");
            return map;
        }
        if (foodOrderMapper.isOrderHasFinishedByOrderNum(orderNum) > 0) {
            map.put("code", ResponseCode.ORDER_HAS_FINISHED.getValue());
            map.put("msg", "订单已完成");
            return map;
        }
        if (!foodOrderMapper.isOrderHasReceivedByOrderNum(orderNum)) {
            map.put("code", ResponseCode.ILLEGAL_REQUEST.getValue());
            map.put("msg", "订单未被接取，无法完成");
            return map;
        }
        foodOrderMapper.addOrderToFinishedOrder(orderNum);
        String receivedId = foodOrderMapper.getReceiverIdByOrderNum(orderNum);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        foodOrderMapper.deleteOrderAfterFinished(orderNum);
        foodOrderMapper.addScoreAndCommentAndTimeToFinishByOrderNum(score,comment,time,orderNum);

        String text = "您好，您接取的订单:" + orderNum + "已完成,感谢您的使用，谢谢";

        //为发布者和接取着增加订单数量
        userOrderService.addPublishOrderNumForUser(userId,1);
        userOrderService.addReceiveOrderNumForUser(receivedId,1,score);

        userOrderMapper.addFinishedOrder(orderNum,publisherId,receivedId);
        userOrderMapper.deletePublishOrder(orderNum);
        userOrderMapper.deleteReceiveOrder(orderNum);

        messageService.SendMessage(Constant.HANDY_OFFICER_ID, Integer.valueOf(receivedId), text);
        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
        return map;

    }
}
