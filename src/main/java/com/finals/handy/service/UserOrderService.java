package com.finals.handy.service;

import com.auth0.jwt.interfaces.Claim;
import com.finals.handy.bean.ExpressOrder;
import com.finals.handy.constant.ResponseCode;
import com.finals.handy.mapper.ExpressOrderMapper;
import com.finals.handy.util.JwtUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zsw
 */
@Service
public class UserOrderService {

    @Autowired
    ExpressOrderMapper expressOrderMapper;


    public Map<String, Object> getMyExpressOrder(String accessToken,int pageNo,int pageSize) {

        Map<String, Object> map = new HashMap<>(16);

        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);

        String id = claimMap.get("userId").asString();

        String orderBy = "id desc";
        PageHelper.startPage(pageNo, pageSize, orderBy);
        Page<ExpressOrder> orders = expressOrderMapper.getMyExpressOrders(id);


        PageInfo<ExpressOrder> pageInfo = new PageInfo<>(orders);


        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());

        map.put("myOrders", pageInfo);

        return map;
    }

}
