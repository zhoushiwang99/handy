package com.finals.handy.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author zsw
 * 用户账户有关的数据库操作
 * 认证，黑名单，取消黑名单,更改手机号
 */
@Mapper
public interface AccountMapper {



    /**
     * 根据手机号获取用户id
     * @param phone
     * @return
     */
    @Select("select id from phone_login where phone = #{phone}")
    int getUserIdByPhone(String phone);

    /**
     * 更新用户信息的手机号
     * @param userId
     * @param phone
     */
    @Update("update user_info set phone = #{phone} where id = #{userId}")
    void resetPhoneInfo(int userId,String phone);

    /**
     * 更新用户登录的手机号
     * @param userId
     * @param phone
     */
    @Update("update phone_login set phone = #{phone} where id = #{userId}")
    void resetPhoneLogin(int userId,String phone);


    /**
     * 解除学号登录限制
     * @param id
     */
    @Update("update xh_login set verify = 1 where id=#{id}")
    void verifyStudentIdAccount(int id);

    /**
     * 解除手机号登录限制
     * @param id
     */
    @Update("update phone_login set verify = 1 where id=#{id}")
    void verifyPhoneAccountById(int id);

    /**
     * 重置密码
     * @param phone 手机号
     * @param password 新密码
     */
    @Update("update phone_login set password = #{password} where phone = #{phone}")
    void resetPasswordByPhone(String phone,String password);




}
