package com.finals.handy.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @author zsw
 * 用户账户有关的数据库操作
 * 认证，黑名单，取消黑名单
 */
@Mapper
public interface AccountMapper {

    /**
     * 解除学号登录限制
     * @param id
     */
    @Update("update xh_login set verify = 1 where id=#{id}")
    public void verifyStudentIdAccount(int id);

    /**
     * 解除手机号登录限制
     * @param id
     */
    @Update("update phone_login set verify = 1 where id=#{id}")
    void verifyPhoneAccountById(int id);






}
