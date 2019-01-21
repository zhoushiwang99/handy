package com.finals.handy.service;

import com.finals.handy.bean.User;
import com.finals.handy.mapper.AccountMapper;
import com.finals.handy.mapper.AddUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author zsw
 * 拉黑用户，取消拉黑
 */
@Service
public class AccountService {

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    AddUserMapper addUserMapper;

    @Transactional(rollbackFor = Exception.class)
    public void verifyAccount(int userId, String studentId, Map<String,Object> infoMap){
        addUserMapper.addStudentIdAccount(userId,studentId);
        addUserInfo(userId,infoMap);
        accountMapper.verifyPhoneAccountById(userId);
        accountMapper.verifyStudentIdAccount(userId);
    }


    private void addUserInfo(int userId,Map<String, Object> map) {

        User user = new User();
        user.setId(userId);
        user.setUsername((String) map.get("姓名"));
        user.setStudentId((String) map.get("学号"));
        user.setClassName((String) map.get("行政班级"));
        user.setRxrq((String) map.get("入学时间"));
        user.setXz((String) map.get("学习年限"));
        user.setSex(map.get("性别").equals("男") ? 'm' : 'f');
        user.setIdNumber((String) map.get("身份证号"));
        user.setPhone((String) map.get("phone"));

        addUserMapper.addUserInfoFromJw(user);
    }


}
