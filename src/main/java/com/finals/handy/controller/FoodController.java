package com.finals.handy.controller;

import com.finals.handy.bean.FoodOrder;
import com.finals.handy.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Map;

/**
 * @author zsw
 */
@RestController
public class FoodController {


    @Autowired
    FoodService foodService;

    @PostMapping("/food/publishOrder")
    public Map<String,Object> publishOrder(String accessToken,@Valid FoodOrder foodOrder,String foodList){
        Map<String, Object> map = foodService.publishOrder(accessToken, foodOrder, foodList);
        return map;
    }

    @PostMapping("/food/receiveOrder")
    public Map<String,Object> receiveOrder(String accessToken,String orderNum){
        Map<String, Object> map = foodService.receiveOrder(accessToken, orderNum);
        return map;
    }

    @PostMapping("/food/getMyPublishedOrder")
    public Map<String,Object> getMyPublishFoodOrder(String accessToken, @RequestParam(defaultValue = "1") int pageNo,@RequestParam(defaultValue = "3") int pageSize){
        Map<String, Object> map = foodService.getMyPublishFoodOrder(accessToken, pageNo, pageSize);
        return map;
    }

    @GetMapping("/guest/listFoodOrders")
    public Map<String,Object> listFoodOrders(@RequestParam(defaultValue = "1") int pageNo,@RequestParam(defaultValue = "3") int pageSize){
        Map<String, Object> map = foodService.listFoodOrders(pageNo, pageSize);
        return map;
    }

    @PostMapping("/food/deleteOrder")
    public Map<String,Object> deleteFoodOrder(String accessToken,String orderNum){
        Map<String, Object> map = foodService.deleteFoodOrder(accessToken, orderNum);
        return map;
    }

    @PostMapping("/food/agreeDeleteOrder")
    public Map<String,Object> agreeDeleteFoodOrder(String accessToken,String orderNum){
        Map<String, Object> map = foodService.agreeDeleteFoodOrder(accessToken, orderNum);
        return map;
    }

    @PostMapping("/food/finishOrder")
    public Map<String,Object> finishOrder(String accessToken,String orderNum,Integer score,@Size(min = 0,max = 40) String comment){
        Map<String, Object> map = foodService.finishOrder(accessToken, orderNum, score, comment);
        return map;
    }


}
