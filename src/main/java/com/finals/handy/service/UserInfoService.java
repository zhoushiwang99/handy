package com.finals.handy.service;

import com.finals.handy.constant.ResponseCode;
import com.finals.handy.mapper.AddUserMapper;
import com.finals.handy.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zsw
 * @date 2019/4/13 20:54
 */
@Service
public class UserInfoService {

    @Autowired
    AddUserMapper addUserMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    HeadImgService headImgService;

    public Map<String, Object> getUserById(Integer userId) {
        Map<String, Object> map = new HashMap<>(16);
        if (addUserMapper.userHasRegisted(userId) != 1) {
            map.put("code", ResponseCode.PARAM_ILLEGAL.getValue());
            map.put("msg", "用户不存在");
            return map;
        } else {
            String name = userInfoMapper.getNameById(userId);
            Character sex = userInfoMapper.getSexById(userId);
            map.put("name", name);
            map.put("sex", sex);
            Map<String, Object> headImg = headImgService.getHeadImg(userId);
            System.out.println(headImg);
            System.out.println("headImgjspodjkpowsd" + headImg.get("headImg"));
            if (headImg.get("headImg") != null) {
                //头像
                map.put("headImg", headImg.get("headImg"));
            }
            Map<String, Object> userScore = headImgService.getUserScore(userId);
            if ((int) userScore.get("code") == 0) {
                map.put("publishNum", userScore.get("publishNum"));
                map.put("receiveNum", userScore.get("receiveNum"));
                map.put("score", userScore.get("score"));
            }
            map.put("code",ResponseCode.REQUEST_SUCCEED.getValue());
            return map;
        }
    }


}
