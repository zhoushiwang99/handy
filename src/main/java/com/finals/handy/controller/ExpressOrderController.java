package com.finals.handy.controller;

import com.finals.handy.bean.ExpressOrder;
import com.finals.handy.service.ExpressOrderService;
import com.finals.handy.service.UserOrderService;
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
public class ExpressOrderController {

    @Autowired
    ExpressOrderService expressOrderService;

    @Autowired
    UserOrderService userOrderService;


    @PostMapping("/express/finishOrder")
    public Map<String,Object> finishOrder(String accessToken,String orderNum,@Size(min=0,max=40) String comment,Integer score){
        Map<String, Object> map = expressOrderService.finishOrder(accessToken, orderNum, comment,score);
        return map;
    }

    @PostMapping("/express/releaseOrder")
    public Map<String, Object> releaseExpressOrder(String accessToken, @Valid ExpressOrder expressOrder) {
        Map<String, Object> map = expressOrderService.releaseFoodOrder(accessToken, expressOrder);
        return map;
    }

    @PostMapping("/express/receiveOrder")
    public Map<String, Object> receiveExpressOrder(String accessToken, String orderNum) {
        Map<String, Object> map = expressOrderService.receiveExpressOrder(accessToken, orderNum);
        return map;
    }

    @GetMapping("/guest/listExpressOrder")
    public Map<String, Object> getPublishedOrder(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "3") int pageSize) {
        Map<String, Object> map = expressOrderService.getPublishedOrder(pageNo, pageSize);
        return map;
    }

    @PostMapping("/user/getMyExpressOrder")
    public Map<String, Object> getMyExpressOrder(String accessToken,@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "3") int pageSize) {
        Map<String, Object> map = userOrderService.getMyExpressOrder(accessToken,pageNo,pageSize);
        return map;
    }

    @PostMapping("/express/deleteOrder")
    public Map<String, Object> deleteExpressOrder(String accessToken, String orderNum) {
        Map<String, Object> map = expressOrderService.deleteExpressOrder(accessToken, orderNum);
        return map;
    }

    @PostMapping("/express/agreeDelete")
    public Map<String,Object> agreeDeleteOrder(String accessToken,String orderNum) {
        Map<String, Object> map = expressOrderService.agreeDeleteOrder(accessToken, orderNum);
        return map;
    }


}
