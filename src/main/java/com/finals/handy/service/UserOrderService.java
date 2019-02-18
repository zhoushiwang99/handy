package com.finals.handy.service;

import com.auth0.jwt.interfaces.Claim;
import com.finals.handy.bean.ExpressOrder;
import com.finals.handy.constant.ResponseCode;
import com.finals.handy.mapper.ExpressOrderMapper;
import com.finals.handy.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zsw
 */
@Service
public class UserOrderService {

    @Autowired
    ExpressOrderMapper expressOrderMapper;


    public Map<String, Object> getMyExpressOrder(String accessToken) {

        Map<String, Object> map = new HashMap<>(16);

        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);

        String id = claimMap.get("userId").asString();

        List<ExpressOrder> orders = expressOrderMapper.getMyExpressOrders(id);

        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());

        map.put("myOrders", orders);

        return map;
    }

}
