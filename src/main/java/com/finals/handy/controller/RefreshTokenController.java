package com.finals.handy.controller;

import com.auth0.jwt.interfaces.Claim;
import com.finals.handy.constant.ResponseCode;
import com.finals.handy.mapper.UserLoginMapper;
import com.finals.handy.service.RedisService;
import com.finals.handy.service.UserLoginService;
import com.finals.handy.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zsw
 */
@RestController
public class RefreshTokenController {

    @Autowired
    UserLoginMapper userLoginMapper;

    @Autowired
    RedisService redisService;

    @PostMapping("/guest/refreshToken")
    public Map<String,Object> refreshToken(String refreshToken){

        System.out.println("refreshToken：" + refreshToken);

        Map<String,Object> map = new HashMap<>(16);

        Map<String, Claim> claimMap = JwtUtil.verifyRefreshToken(refreshToken);


        int userId = Integer.parseInt(claimMap.get("userId").asString());
        String redisKey = UserLoginService.USER_TOKEN_PREFIX + userId;

        if(userLoginMapper.isUserBlackById(userId)){
            map.put("code",ResponseCode.USER_IS_BLACK.getValue());
            return map;
        }
        else if(redisService.exist(redisKey) && redisService.get(redisKey).equals(refreshToken)){

            String newAccessToken = JwtUtil.createAccessToken(userId);
            String newRefreshToken = JwtUtil.createRefreshToken(userId);

            redisService.set("user:token:" + userId,newRefreshToken, UserLoginService.TOKEN_EXPIRE_TIME);

            map.put("code",ResponseCode.REQUEST_SUCCEED.getValue());
            map.put("accessToken",newAccessToken);
            map.put("refreshToken",newRefreshToken);
            return map;
        }else{
            map.put("code",ResponseCode.USER_LOGIN_OTHER.getValue());
            return map;
        }
    }





}
