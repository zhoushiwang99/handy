package com.finals.handy.controller;

import com.auth0.jwt.interfaces.Claim;
import com.finals.handy.service.HeadImgService;
import com.finals.handy.service.UserInfoService;
import com.finals.handy.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    UserInfoService userInfoService;

    @PostMapping("/user/getUserScore")
    public Map<String,Object> getUserScore(@RequestParam("accessToken") String accessToken){
        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);
        Integer userId = Integer.valueOf(claimMap.get("userId").asString());
        Map<String, Object> map = userService.getUserScore(userId);
        return map;
    }

    @PostMapping("/user/setHeadImg")
    public Map<String, Object> setHeadImg(@RequestParam(value = "accessToken") String accessToken
            , @RequestParam(value = "headImg") MultipartFile headImg) {
        Map<String, Object> map = userService.setHeadImg(accessToken, headImg);
        return map;
    }

    @PostMapping("/user/getHeadImg")
    public Map<String,Object> getHeadImg(@RequestParam("accessToken") String accessToken){
        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);
        Integer userId = Integer.valueOf(claimMap.get("userId").asString());
        Map<String, Object> map = userService.getHeadImg(userId);
        return map;
    }

    @PostMapping("/user/getUserInfo")
    public Map<String,Object> getUserInfo(@RequestParam("accessToken")String accessToken){
        Map<String, Object> map = userService.getUserInfo(accessToken);
        return map;
    }

    @GetMapping("/guest/getUserById")
    public Map<String,Object> getUserById(@RequestParam("userId")Integer userId){
        return userInfoService.getUserById(userId);
    }


}
