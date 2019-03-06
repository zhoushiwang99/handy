package com.finals.handy.service;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.Claim;
import com.finals.handy.bean.Food;
import com.finals.handy.bean.FoodOrder;
import com.finals.handy.constant.ResponseCode;
import com.finals.handy.util.GenerateNumUtil;
import com.finals.handy.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zsw
 */
@Service
public class FoodService {

    @Autowired
    GenerateNumUtil generateNumUtil;

    public Map<String,Object> publishOrder(String accessToken, FoodOrder foodOrder, String foodList){
        Map<String,Object> map = new HashMap<>(16);
        List<Food> foods = JSON.parseArray(foodList,Food.class);
        if(foods.size() > 10){
            map.put("code", ResponseCode.PARAM_ILLEGAL.getValue());
            map.put("msg","食物数量超出10个");
            return map;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        foodOrder.setPublishTime(sdf.format(new Date()));
        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);
        String userId = claimMap.get("userId").asString();
        foodOrder.setPublisherId(userId);
        double totalMoney = 0;
        for(int i = 0;i < foods.size();i++){
            totalMoney += foods.get(i).getMoney();
        }
        foodOrder.setTotalMoney(totalMoney);
        foodOrder.setFoods(foods);
        foodOrder.setOrderNumber(generateNumUtil.getFoodOrderNumber());

        return null;
    }


}
