package com.finals.handy.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author zsw
 * @date 2019/4/13 20:57
 */
@Mapper
public interface UserInfoMapper {

    /**
     * 根据id获取用户姓名
     * @param userId
     * @return
     */
    @Select("select username from user_info where id = #{userId}")
    String getNameById(Integer userId);

    /**
     * 根据id获取用户性别
     * @param userId
     * @return
     */
    @Select("select sex from user_info where id = #{userId}")
    Character getSexById(Integer userId);
}
