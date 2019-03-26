package com.finals.handy.mapper;


import com.finals.handy.bean.User;
import org.apache.ibatis.annotations.*;

/**
 * @author zsw
 */
@Mapper
public interface AddUserMapper {

    /**
     * 添加用户手机号信息
     * @param user
     */
    @Insert("insert into user_info(phone) values(#{phone})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void addUserPhone(User user);

    /**
     * 添加手机号登录(此时尚未验证，无法登录)
     * @param user
     */
    @Insert("insert into phone_login(id,phone,password) values(#{id},#{phone},#{password})")
    void addPhoneLogin(User user);

    /**
     * 添加用户基本信息
     * @param user
     */
    @Update("update user_info set username = #{username},student_id = #{studentId},class_name = #{className}," +
            "rxrq = #{rxrq}, sex = #{sex},id_number = #{idNumber},study_year = #{xz}" +
            "where id = #{id}")
    void addUserInfoFromJw(User user);

    /**
     * 根据手机号获取用户id
     * @param phone
     * @return
     */
    @Select("select id from phone_login where phone = #{phone}")
    int getUserIdByPhone(String phone);




    @Update("update phone_login set verify = 1 where id=#{id}")
    void verifyPhoneAccountById(int id);

    /**
     * 为用户添加学号登录（仍处于限制）
     * @param userId
     * @param studentId
     */
    @Insert("insert into xh_login(id,student_id) values (#{userId},#{studentId})")
    public void addStudentIdAccount(int userId,String studentId);

    /**
     * 学号是否已被注册
     * @param studentId
     * @return
     */
    @Select("select count(*) from user_info where student_id = #{studentId}")
    public int studentIdHasRegisted(String studentId);

    /**
     * 手机号是否已注册
     * @param phone
     * @return
     */
    @Select("select count(*) from user_info where phone = #{phone}")
    public int phoneHasRegisted(String phone);

    /**
     * 用户是否已注册
     * @param id
     * @return
     */
    @Select("select count(*) from user_info where id = #{id}")
    public int userHasRegisted(int id);

}
