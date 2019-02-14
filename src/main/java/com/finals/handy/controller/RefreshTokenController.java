package com.finals.handy.controller;

import com.auth0.jwt.interfaces.Claim;
import com.finals.handy.constant.ResponseCode;
import com.finals.handy.mapper.UserLoginMapper;
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

    @PostMapping("/guest/refreshToken")
    public Map<String,Object> refreshToken(String refreshToken){

        Map<String,Object> map = new HashMap<>(16);

        Map<String, Claim> claimMap = JwtUtil.verifyRefreshToken(refreshToken);
        System.out.println(claimMap.get("userId").asString());
        int userId = Integer.parseInt(claimMap.get("userId").asString());
        if(userLoginMapper.isUserBlackById(userId)){
            map.put("code",ResponseCode.USER_IS_BLACK.getValue());
            return map;
        }else{

            String newAccessToken = JwtUtil.createAccessToken(userId);
            String newRefreshToken = JwtUtil.createRefreshToken(userId);

            map.put("code",ResponseCode.REQUEST_SUCCEED.getValue());
            map.put("accessToken",newAccessToken);
            map.put("refreshToken",newRefreshToken);
            return map;
        }
    }





}
