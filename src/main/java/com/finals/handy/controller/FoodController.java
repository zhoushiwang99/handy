package com.finals.handy.controller;

import com.alibaba.fastjson.JSON;
import com.finals.handy.bean.Food;
import com.finals.handy.bean.FoodOrder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author zsw
 */
@RestController
public class FoodController {

    @PostMapping("/food/publishOrder")
    public Map<String,Object> publishOrder(String accessToken,@Valid FoodOrder foodOrder,String foodList){
        List<Food> foods = JSON.parseArray(foodList,Food.class);
        System.out.println(foods.size());
        return null;
    }


}
