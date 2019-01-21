package com.finals.handy.controller;

import com.auth0.jwt.interfaces.Claim;
import com.finals.handy.constant.ResponseCode;
import com.finals.handy.mapper.UserLoginMapper;
import com.finals.handy.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zsw
 */
@Controller
public class RefreshTokenController {

    @Autowired
    UserLoginMapper userLoginMapper;

    @PostMapping("/guest/refreshToken")
    public Map<String,Object> refreshToken(String refreshToken){

        Map<String,Object> map = new HashMap<>(16);

        Map<String, Claim> claimMap = JwtUtil.verifyRefreshToken(refreshToken);
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
