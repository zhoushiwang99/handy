package com.finals.handy.controller;

import com.finals.handy.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zsw
 * @date 2019/3/8 13:11
 */
@RestController()
public class UserOrderController {

    @Autowired
    UserOrderService userOrderService;

    @GetMapping("/user/getMyPublishOrders")
    public Map<String, Object> getUserPublishedOrders(@RequestParam String accessToken, @RequestParam(defaultValue = "1") int pageNo,
                                                      @RequestParam(defaultValue = "3") int pageSize) {
        return userOrderService.getUserPublishedOrders(accessToken, pageNo, pageSize);
    }

    @GetMapping("/user/getMyReveiveOrders")
    public Map<String,Object> getUserReceivedOrders(@RequestParam String accessToken, @RequestParam(defaultValue = "1") int pageNo,
                                                    @RequestParam(defaultValue = "3") int pageSize){
        return userOrderService.getUserReceivedOrders(accessToken, pageNo, pageSize);
    }

    @GetMapping("/user/getFinishedReceiveOrders")
    public Map<String,Object> getUserFinishReceivedOrders(@RequestParam String accessToken, @RequestParam(defaultValue = "1") int pageNo,
                                                    @RequestParam(defaultValue = "3") int pageSize){
        return userOrderService.getUserFinishReceivedOrders(accessToken, pageNo, pageSize);
    }

    @GetMapping("/user/getFinishedPublishOrders")
    public Map<String,Object> getUserFinishPublishedOrders(@RequestParam String accessToken, @RequestParam(defaultValue = "1") int pageNo,
                                                           @RequestParam(defaultValue = "3") int pageSize){
        return userOrderService.getUserFinishPublishedOrders(accessToken, pageNo, pageSize);
    }


}
