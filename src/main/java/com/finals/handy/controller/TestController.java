package com.finals.handy.controller;

import com.finals.handy.mapper.AccountMapper;
import com.finals.handy.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {

    @Autowired
    AccountMapper accountMapper;

    @GetMapping("/guest/hello")
    public Map<String,Object> get(){
        UserInfo info = accountMapper.getUserInfoByUserId("11");
        System.out.println(info);
        return null;
    }



}
