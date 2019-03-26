package com.finals.handy.service;

import com.auth0.jwt.interfaces.Claim;
import com.finals.handy.mapper.AdviceMapper;
import com.finals.handy.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author zsw
 * @date 2019/3/17 21:19
 */
@Service
public class AdviceService {

    @Autowired
    AdviceMapper adviceMapper;

    public boolean addAdvice(String accessToken,String advice){
        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);
        String userId = claimMap.get("userId").asString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        adviceMapper.addAdvice(advice,userId,time);
        return true;
    }
}
