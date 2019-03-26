package com.finals.handy.controller;

import com.finals.handy.constant.ResponseCode;
import com.finals.handy.service.AdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zsw
 * @date 2019/3/17 21:18
 */
@RestController
public class AdviceController {

    @Autowired
    AdviceService adviceService;

    @PostMapping("/user/sendAdvice")
    public Map<String,Object> sendAdvice(String accessToken,
                                         @Valid @Size(min = 10,max = 100) @RequestParam("advice")String advice){
        adviceService.addAdvice(accessToken, advice);
        Map<String,Object> map = new HashMap<>();
        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
        return map;
    }


}
