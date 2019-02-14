package com.finals.handy.mapper;

import com.finals.handy.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author zsw
 * 用户登录时调用数据库的方法
 */
@Mapper
public interface UserLoginMapper {


    /**
     * 根据学号获取用户id
     * @param studentId
     * @return
     */
    @Select("select id from xh_login where student_id=#{studentId}")
    public int getUserIdByStudentId(String studentId);

    /**
     * 根据用户手机号查询用户是否经过实名认证
     * @param phone 手机号
     * @return
     */
    @Select("select verify from phone_login where phone = #{phone}")
    public boolean isUserVerifyByPhone(String phone);


    /**
     * 根据用户手机号
     * @param id
     * @return
     */
    @Select("select black_user from phone_login where id = #{id}")
    public boolean isUserBlackById(int id);


    @Select("select black_user from phone_login where phone = #{phone}")
    public boolean isUserBlackByPhone(String phone);


    /**
     * 根据用户手机号获取密码
     * @param phone
     * @return
     */
    @Select("select password from phone_login where phone = #{phone}")
    public String getPhonePassword(String phone);

    /**
     * 检查手机号是否存在
     * @param phone
     * @return
     */
    @Select("select count(*) from phone_login where phone = #{phone}")
    public int phoneExist(String phone);


    @Select("select count(*) from xh_login where student_id = #{studentId}")
    public int xhExist(String studentId);

    /**
     * 根据手机号获取用户角色
     * @param phone
     * @return
     */
    @Select("select role from phone_login where phone = #{phone}")
    public String getRoleByPhone(String phone);

    /**
     * 根据手机号获取用户名字
     * @param phone
     * @return
     */
    @Select("select username from user_info where phone = #{phone}")
    public String getUserNameByPhone(String phone);

    /**
     * 根据手机号获取用户信息
     * @param phone
     * @return
     */
    @Select("select * from user_info where phone = #{phone}")
    public User getUserByPhone(String phone);

    /**
     * 根据学号获取用户信息
     * @param studentId
     * @return
     */
    @Select("select * from user_info where student_id = #{studentId}")
    public User getUserByStudentId(String studentId);

    /**
     * 根据学号获取手机号
     * @param studentId
     * @return
     */
    @Select("select phone from user_info where student_id = #{studentId}")
    public String getPhoneByStudentId(String studentId);

    /**
     * 学号是否存在
     * @param studentId
     * @return
     */
    @Select("select count(*) from xh_login  where student_id = #{studentId}")
    public int studentIdExist(String studentId);

    /**
     * 学号登录判断黑名单
     * @param studentId
     * @return
     */
    @Select("select black_user from xh_login where student_id = #{studentId}")
    public boolean isUserBlackByStudentId(String studentId);

    /**
     * 学号登录判断是否已实名
     * @param studentId
     * @return
     */
    @Select("select verify from xh_login where student_id = #{studentId}")
    public boolean isUserVerifyByStudentId(String studentId);
}
