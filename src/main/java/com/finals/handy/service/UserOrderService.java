package com.finals.handy.service;

import com.auth0.jwt.interfaces.Claim;
import com.finals.handy.bean.ExpressOrder;
import com.finals.handy.bean.Food;
import com.finals.handy.bean.FoodOrder;
import com.finals.handy.bean.SpecialtyOrder;
import com.finals.handy.constant.ResponseCode;
import com.finals.handy.mapper.*;
import com.finals.handy.util.GenerateNumUtil;
import com.finals.handy.util.JwtUtil;
import com.finals.handy.vo.FinishedExpressOrder;
import com.finals.handy.vo.FinishedFoodOrder;
import com.finals.handy.vo.FinishedSpecialtyOrder;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zsw
 */
@Service
public class UserOrderService {

    @Autowired
    ExpressOrderMapper expressOrderMapper;

    @Autowired
    FoodOrderMapper foodOrderMapper;

    @Autowired
    SpecialtyMapper specialtyMapper;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    UserOrderMapper userOrderMapper;


    public Map<String, Object> getUserFinishPublishedOrders(String accessToken, int pageNo, int pageSize) {
        Map<String, Object> map = new HashMap<>(16);

        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);

        String userId = claimMap.get("userId").asString();


        String orderBy = "id desc";
        PageHelper.startPage(pageNo, pageSize, orderBy);

        Page<String> orderNums = userOrderMapper.getUserFinishedPublishOrderNum(userId);

        int i = 1;

        for (String orderNum : orderNums) {
            if (String.valueOf(orderNum.charAt(0)).equals(GenerateNumUtil.FOOD_Order_PREFIX)) {
                FinishedFoodOrder foodOrder = foodOrderMapper.getFinishedOrderByNum(orderNum);
                List<Food> foods = foodOrderMapper.getFoodsByOrderNum(orderNum);
                foodOrder.setFoods(foods);
                map.put(i + ":food", foodOrder);
                i++;
            } else if (String.valueOf(orderNum.charAt(0)).equals(GenerateNumUtil.EXPRESS_ORDER_PREFIX)) {
                FinishedExpressOrder expressOrder = expressOrderMapper.getOrderFromFinishedByNum(orderNum);
                map.put(i + ":express", expressOrder);
                i++;
            } else if (String.valueOf(orderNum.charAt(0)).equals(GenerateNumUtil.SPECIALTY_ORDER_PREFIX)) {
                FinishedSpecialtyOrder specialtyOrder = specialtyMapper.getFinishedOrderByNum(orderNum);
                map.put(i + ":specialty", specialtyOrder);
                i++;
            }
        }
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        return map;
    }


    public Map<String, Object> getUserFinishReceivedOrders(String accessToken, int pageNo, int pageSize) {
        Map<String, Object> map = new HashMap<>(16);

        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);

        String userId = claimMap.get("userId").asString();


        String orderBy = "id desc";
        PageHelper.startPage(pageNo, pageSize, orderBy);

        Page<String> orderNums = userOrderMapper.getUserFinishedReceiveOrderNum(userId);

        int i = 1;

        for (String orderNum : orderNums) {
            if (String.valueOf(orderNum.charAt(0)).equals(GenerateNumUtil.FOOD_Order_PREFIX)) {
                FinishedFoodOrder foodOrder = foodOrderMapper.getFinishedOrderByNum(orderNum);
                List<Food> foods = foodOrderMapper.getFoodsByOrderNum(orderNum);
                foodOrder.setFoods(foods);
                map.put(i + ":food", foodOrder);
                i++;
            } else if (String.valueOf(orderNum.charAt(0)).equals(GenerateNumUtil.EXPRESS_ORDER_PREFIX)) {
                FinishedExpressOrder expressOrder = expressOrderMapper.getOrderFromFinishedByNum(orderNum);
                map.put(i + ":express", expressOrder);
                i++;
            } else if (String.valueOf(orderNum.charAt(0)).equals(GenerateNumUtil.SPECIALTY_ORDER_PREFIX)) {
                FinishedSpecialtyOrder specialtyOrder = specialtyMapper.getFinishedOrderByNum(orderNum);
                map.put(i + ":specialty", specialtyOrder);
                i++;
            }
        }
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        return map;
    }


    public Map<String, Object> getUserReceivedOrders(String accessToken, int pageNo, int pageSize) {
        Map<String, Object> map = new HashMap<>(16);

        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);

        String userId = claimMap.get("userId").asString();


        String orderBy = "id desc";
        PageHelper.startPage(pageNo, pageSize, orderBy);

        Page<String> orderNums = userOrderMapper.getUserReceivedOrderNum(userId);

        int i = 1;

        for (String orderNum : orderNums) {
            if (String.valueOf(orderNum.charAt(0)).equals(GenerateNumUtil.FOOD_Order_PREFIX)) {
                FoodOrder order = foodOrderMapper.getOrderByOrderNum(orderNum);
                List<Food> foods = foodOrderMapper.getFoodsByOrderNum(orderNum);
                order.setFoods(foods);
                map.put(i + ":food", order);
                i++;
            } else if (String.valueOf(orderNum.charAt(0)).equals(GenerateNumUtil.EXPRESS_ORDER_PREFIX)) {
                ExpressOrder order = expressOrderMapper.getExpressOrderByOrderNum(orderNum);
                map.put(i + ":express", order);
                i++;
            } else if (String.valueOf(orderNum.charAt(0)).equals(GenerateNumUtil.SPECIALTY_ORDER_PREFIX)) {
                SpecialtyOrder order = specialtyMapper.getOrderByOrderNum(orderNum);
                map.put(i + ":specialty", order);
                i++;
            }
        }
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        return map;
    }

    public Map<String, Object> getUserPublishedOrders(String accessToken, int pageNo, int pageSize) {
        Map<String, Object> map = new HashMap<>(16);

        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);

        String userId = claimMap.get("userId").asString();


        String orderBy = "id desc";
        PageHelper.startPage(pageNo, pageSize, orderBy);

        Page<String> orderNums = userOrderMapper.getUserPublishedOrderNum(userId);

        int i = 1;

        for (String orderNum : orderNums) {
            if (String.valueOf(orderNum.charAt(0)).equals(GenerateNumUtil.FOOD_Order_PREFIX)) {
                FoodOrder order = foodOrderMapper.getOrderByOrderNum(orderNum);

                List<Food> foods = foodOrderMapper.getFoodsByOrderNum(orderNum);
                order.setFoods(foods);
                map.put(i + ":food", order);
                i++;
            } else if (String.valueOf(orderNum.charAt(0)).equals(GenerateNumUtil.EXPRESS_ORDER_PREFIX)) {
                ExpressOrder order = expressOrderMapper.getExpressOrderByOrderNum(orderNum);
                map.put(i + ":express", order);
                i++;
            } else if (String.valueOf(orderNum.charAt(0)).equals(GenerateNumUtil.SPECIALTY_ORDER_PREFIX)) {
                SpecialtyOrder order = specialtyMapper.getOrderByOrderNum(orderNum);
                map.put(i + ":specialty", order);
                i++;
            }
        }

        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        return map;
    }


    @Transactional(rollbackFor = Exception.class)
    public void addPublishOrderNumForUser(String userId, int num) {
        //判断评分表里面是否有用户信息
        if (accountMapper.ifUserHasScoreByUserId(userId) != 1) {
            accountMapper.addUserScoreInfo(userId);
        }

        int publishNum = accountMapper.getUserPublishOrderNumByUserId(userId);
        publishNum += num;
        accountMapper.setPublishOrderNumsByUserId(userId, publishNum);

    }


    @Transactional(rollbackFor = Exception.class)
    public void addReceiveOrderNumForUser(String userId, int num, int score) {
        //判断评分表里面是否有用户信息
        if (accountMapper.ifUserHasScoreByUserId(userId) != 1) {
            accountMapper.addUserScoreInfo(userId);
        }
        int receiveNum = accountMapper.getReceiveOrderNumByUserId(userId);
        receiveNum += num;
        //增加用户完成的订单数量
        accountMapper.setReceiveOrderNumsByUserId(userId, receiveNum);
        //设置用户评分
        int nowScore = accountMapper.getScoreByUserId(userId);
        nowScore += score;
        accountMapper.setUserScoreByUserId(userId, nowScore);
    }


    public Map<String, Object> getMyExpressOrder(String accessToken, int pageNo, int pageSize) {

        Map<String, Object> map = new HashMap<>(16);

        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);

        String id = claimMap.get("userId").asString();

        String orderBy = "id desc";
        PageHelper.startPage(pageNo, pageSize, orderBy);
        Page<ExpressOrder> orders = expressOrderMapper.getMyExpressOrders(id);


        PageInfo<ExpressOrder> pageInfo = new PageInfo<>(orders);


        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());

        map.put("myOrders", pageInfo);

        return map;
    }

}
