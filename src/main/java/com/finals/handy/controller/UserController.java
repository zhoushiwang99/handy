package com.finals.handy.controller;

import com.finals.handy.service.HeadImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author zsw
 */
@RestController
public class UserController {

    @Autowired
    HeadImgService userService;

    @PostMapping("/user/getUserScore")
    public Map<String,Object> getUserScore(String accessToken){
        Map<String, Object> map = userService.getUserScore(accessToken);
        return map;
    }

    @PostMapping("/user/setHeadImg")
    public Map<String, Object> setHeadImg(@RequestParam(value = "accessToken") String accessToken
            , @RequestParam(value = "headImg") MultipartFile headImg) {
        Map<String, Object> map = userService.setHeadImg(accessToken, headImg);
        return map;
    }

    @PostMapping("/user/getHeadImg")
    public Map<String,Object> getHeadImg(String accessToken){
        Map<String, Object> map = userService.getHeadImg(accessToken);
        return map;
    }

    @PostMapping("/user/getUserInfo")
    public Map<String,Object> getUserInfo(String accessToken){
        Map<String, Object> map = userService.getUserInfo(accessToken);
        return map;
    }

}
