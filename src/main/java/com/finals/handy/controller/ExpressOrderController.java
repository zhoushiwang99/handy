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

    @PostMapping("/express/releaseOrder")
    public Map<String, Object> releaseExpressOrder(String accessToken, @Valid ExpressOrder expressOrder) {
        Map<String, Object> map = expressOrderService.releaseFoodOrder(accessToken, expressOrder);
        return map;
    }

    @PostMapping("/express/receiveOrder")
    public Map<String, Object> receiveExpressOrder(String accessToken, int expressOrderId) {
        Map<String, Object> map = expressOrderService.receiveExpressOrder(accessToken, expressOrderId);
        return map;
    }

    @GetMapping("/guest/listExpressOrder")
    public Map<String, Object> getPublishedOrder(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "3") int pageSize) {
        Map<String, Object> map = expressOrderService.getPublishedOrder(pageNo, pageSize);
        return map;
    }

    @GetMapping("/user/getMyExpressOrder")
    public Map<String, Object> getMyExpressOrder(String accessToken) {
        Map<String, Object> map = userOrderService.getMyExpressOrder(accessToken);
        return map;
    }

    @GetMapping("/express/deleteOrder")
    public Map<String, Object> deleteExpressOrder(String accessToken, String expressOrderId) {
        return null;
    }


}
