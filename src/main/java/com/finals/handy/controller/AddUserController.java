package com.finals.handy.controller;

import com.finals.handy.service.AddUserService;
import com.finals.handy.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zsw
 */
@RestController
public class AddUserController {

    @Autowired
    AddUserService addUserService;

    @Autowired
    RedisService redisService;

    @PostMapping("/guest/addUserByPhone")
    public Map<String, Object> addUserByPhone(String phone, String password, String code) {
        Map<String, Object> map = addUserService.addUserByPhone(phone, password, code);
        return map;
    }


    @GetMapping("/guest/sendRegMessage")
    public Map<String, Object> sendMessage(String phone) {
        Map<String, Object> map = addUserService.sendMessage(phone);
        return map;
    }


    @GetMapping("/guest/getJwCode")
    public Map<String, Object> getJwCode() {
        Map<String, Object> map = addUserService.getJwCode();
        return map;
    }

    @PostMapping("/guest/verifyAccount")
    public Map<String, Object> verifyAccount(int userId, String viewState, String password, String checkCode,
                                             String studentId, String psdLen, String jwCookie) {
        return addUserService.verifyAccount(userId, viewState, password, checkCode,
                studentId, psdLen, jwCookie);
    }


}
