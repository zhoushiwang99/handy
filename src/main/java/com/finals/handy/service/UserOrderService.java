package com.finals.handy.service;

import com.auth0.jwt.interfaces.Claim;
import com.finals.handy.bean.ExpressOrder;
import com.finals.handy.constant.ResponseCode;
import com.finals.handy.mapper.AccountMapper;
import com.finals.handy.mapper.ExpressOrderMapper;
import com.finals.handy.util.JwtUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zsw
 */
@Service
public class UserOrderService {

    @Autowired
    ExpressOrderMapper expressOrderMapper;

    @Autowired
    AccountMapper accountMapper;

    @Transactional(rollbackFor = Exception.class)
    public void addPublishOrderNumForUser(String userId,int num){
        //判断评分表里面是否有用户信息
        if(accountMapper.ifUserHasScoreByUserId(userId) != 1){
            accountMapper.addUserScoreInfo(userId);
        }

        int publishNum = accountMapper.getUserPublishOrderNumByUserId(userId);
        publishNum += num;
        accountMapper.setPublishOrderNumsByUserId(userId,publishNum);

    }


    @Transactional(rollbackFor = Exception.class)
    public void addReceiveOrderNumForUser(String userId,int num,int score){
        //判断评分表里面是否有用户信息
        if(accountMapper.ifUserHasScoreByUserId(userId) != 1){
            accountMapper.addUserScoreInfo(userId);
        }
        int receiveNum = accountMapper.getReceiveOrderNumByUserId(userId);
        receiveNum += num;
        //增加用户完成的订单数量
        accountMapper.setReceiveOrderNumsByUserId(userId,receiveNum);
        //设置用户评分
        int nowScore = accountMapper.getScoreByUserId(userId);
        nowScore += score;
        accountMapper.setUserScoreByUserId(userId,nowScore);
    }


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
