package com.finals.handy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author zsw
 */
@Mapper
public interface UserHeadImgMapper {

    /**
     * 第一次设置头像
     *
     * @param userId 用户id
     * @param path   用户头像路径
     */
    @Insert("insert into user_head_img(user_id,head_img_path) values (#{userId},#{path})")
    void firstHeadImgByUserId(String userId, String path);

    /**
     * 更新用户头像
     * @param userId
     * @param path
     */
    @Update("update user_head_img set head_img_path = #{path} where user_id = #{userId}")
    void updateHeadImgByUserId(String userId,String path);


    /**
     * 用户是否已设置过头像
     * 返回1代表已设置
     * @param userId
     * @return
     */
    @Select("select count(*) from user_head_img where user_id = #{userId}")
    int ifUserHasSetHeadImg(String userId);

    /**
     * 根据用户id获取用户头像路径
     * @param userId
     * @return
     */
    @Select("select head_img_path from user_head_img where user_id = #{userId}")
    String getHeadImgPathByUserId(String userId);



}
