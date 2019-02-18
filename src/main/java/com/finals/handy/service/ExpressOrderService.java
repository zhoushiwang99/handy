package com.finals.handy.service;

import com.auth0.jwt.interfaces.Claim;
import com.finals.handy.bean.ExpressOrder;
import com.finals.handy.constant.ResponseCode;
import com.finals.handy.mapper.ExpressOrderMapper;
import com.finals.handy.util.GenerateNumUtil;
import com.finals.handy.util.JwtUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zsw
 */
@Service
public class ExpressOrderService {

    @Autowired
    ExpressOrderMapper expressOrderMapper;

    @Autowired
    GenerateNumUtil generateNumUtil;

    public Map<String,Object> deleteExpressOrder(String accessToken,String expressOrderId){

        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);
        String userId = claimMap.get("userId").asString();

        return null;
    }


    public Map<String, Object> getPublishedOrder(int pageNo, int pageSize) {
        String orderBy = "id asc";
        Map<String,Object> map = new HashMap<>(16);
        PageHelper.startPage(pageNo,pageSize,orderBy);
        Page<ExpressOrder> expressOrders = expressOrderMapper.getPublishedExpressOrder();

        PageInfo<ExpressOrder> pageInfo = new PageInfo<>(expressOrders);
        map.put("expressOrder",pageInfo);
        map.put("code","0");
        return map;
    }


    /**
     * 发布代领快递订单
     *
     * @param expressOrder
     * @return
     */
    public Map<String, Object> releaseFoodOrder(String accessToken, ExpressOrder expressOrder) {
        Map<String, Object> map = new HashMap<>(16);
        String[] strings = new String[]{"东门", "西门", "南门", "弘二"};

        boolean isSame = false;
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].equals(expressOrder.getPickupDoor())) {
                isSame = true;
                break;
            }
        }
        if (!isSame) {
            map.put("code", ResponseCode.PARAM_ILLEGAL.getValue());
            return map;
        }


        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);

        int publisherId = Integer.parseInt(claimMap.get("userId").asString());

        expressOrder.setPublisherId(publisherId);

        expressOrder.setOrderNumber(generateNumUtil.getExpressOrderNumber());

        expressOrderMapper.realseExpressOrder(expressOrder);
        map.put("expressOrderId", expressOrder.getId());
        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());
        return map;
    }

    public Map<String, Object> receiveExpressOrder(String accessToken, int expressOrderId) {

        Map<String, Object> map = new HashMap<>(16);
        //订单是否存在
        if (expressOrderMapper.isOrderExistByOrderId(expressOrderId) != 1) {
            map.put("code", ResponseCode.ORDER_NOT_EXIST.getValue());
            return map;
        }

        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(accessToken);
        int userId = Integer.parseInt(claimMap.get("userId").asString());
        int publisherId = expressOrderMapper.getPublisherIdByOrderId(expressOrderId);
        //订单发布者和接取者是否为同一个人
        if (userId == publisherId) {
            map.put("code", ResponseCode.ORDER_PUBLISH_BY_ONE.getValue());
            return map;
        }

        boolean isReceived = expressOrderMapper.isOrderReceived(expressOrderId);

        if (isReceived) {
            map.put("code", ResponseCode.ORDER_HAS_RECEIVED.getValue());
            return map;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String time = sdf.format(new Date());

        expressOrderMapper.receiveExpressOrder(expressOrderId, userId, time);

        map.put("code", ResponseCode.REQUEST_SUCCEED.getValue());

        return map;
    }

}
