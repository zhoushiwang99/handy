package com.finals.handy.controller;

import com.finals.handy.service.UserLoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zsw
 */
@RestController
@RequestMapping("/guest")
public class UserLoginController {

    @Autowired
    UserLoginService userLoginService;

    @PostMapping("/phoneLogin")
    public Map<String,Object> login(String phone,String password) {
        Map<String,Object> map;
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()) {
            //把用户名和密码封装为 UsernamePasswordToken 对象
            System.out.println("login request...");
            map = userLoginService.userPhoneLogin(phone, password);
        }else{
            map = new HashMap<>(16);
        }
        return map;
    }

    @PostMapping("/xhLogin")
    public Map<String,Object> xhLogin(String viewState, String password, String checkCode,
                                      String studentId, String psdLen, String jwCookie){
        Map<String, Object> map = userLoginService.xhLogin(viewState, password,checkCode,studentId,psdLen,jwCookie);
        return map;
    }





}
