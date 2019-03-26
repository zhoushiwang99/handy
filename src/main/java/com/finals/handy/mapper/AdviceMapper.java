package com.finals.handy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zsw
 * @date 2019/3/17 21:29
 */
@Mapper
public interface AdviceMapper {

    /**
     * 添加建议
     * @param advice
     * @param userId
     * @param time
     */
    @Insert("insert into advice(advisor_id,advice,time)values(#{userId},#{advice},#{time})")
    void addAdvice(String advice, String userId, String time);

}
