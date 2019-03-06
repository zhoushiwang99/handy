package com.finals.handy.mapper;

import com.finals.handy.vo.UserInfo;
import org.apache.ibatis.annotations.Insert;
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


    @Select("select sum_score from user_score where user_id = #{userId}")
    int getUserSumScoreByUserId(String userId);

    /**
     * 根据用户id设置用户发布订单数
     * @param userId
     */
    @Update("update user_score set publish_order_num = #{num} where user_id = #{userId}")
    void setUserPublishOrderNumByUserId(String userId);

    /**
     * 获取用户发布的订单数
     * @param userId
     * @return
     */
    @Select("select publish_order_num from user_score where user_id = #{userId}")
    int getUserPublishOrderNumByUserId(String userId);

    /**
     * 获取用户接取的订单数
     * @param userId
     * @return
     */
    @Select("select finish_order_num from user_score where user_id = #{userId}")
    int getReceiveOrderNumByUserId(String userId);


    /**
     * 用户评分是否已存
     * @param userId
     * @return
     */
    @Select("select count(*) from user_score where user_id = #{userId}")
    int ifUserHasScoreByUserId(String userId);

    /**
     * 根据用户id设置用户已完成订单数量
     * @param userId
     * @param num
     */
    @Update("update user_score set finish_order_num = #{num} where user_id = #{userId}")
    void setReceiveOrderNumsByUserId(String userId,int num);

    /**
     * 根据用户id设置用户已发布订单数量
     * @param userId
     * @param num
     */
    @Update("update user_score set publish_order_num = #{num} where user_id = #{userId}")
    void setPublishOrderNumsByUserId(String userId,int num);

    /**
     * 根据用户id获取用户当前评分
     * @param userId
     * @return
     */
    @Select("select sum_score from user_score where user_id = #{userId}")
    int getScoreByUserId(String userId);

    /**
     * 根据用户id设置用户评分
     * @param userId
     * @param score
     */
    @Update("update user_score set sum_score = #{score} where user_id = #{userId}")
    void setUserScoreByUserId(String userId,double score);


    /**
     * 在用户评分表里面添加用户id
     * @param userId
     */
    @Insert("insert into user_score(user_id) values (#{userId})")
    void addUserScoreInfo(String userId);



    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    @Select("select * from user_info where id = #{id}")
    UserInfo getUserInfoByUserId(String userId);

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
