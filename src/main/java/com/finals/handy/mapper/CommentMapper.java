package com.finals.handy.mapper;

import com.finals.handy.bean.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author xiaoqiang
 * @date $(DATE)-$(TIME)
 */
@Mapper
public interface CommentMapper {
    //    增加评论
    @Insert("insert into comment(userId,content,isReport,time,taskId) values(#{userId},#{content},#{isReport},#{time},#{taskId})")
    boolean addComment(Comment comment);

    //  删除评论
    @Delete("delete from comment where id=#{id}")
    boolean deleteComment(Integer id);

    //    举报评论
    @Update("update comment set isReport=#{isReport} where id=#{id}")
    boolean reportComment(Integer isReport, Integer id);

//    查找n个评论
    @Select("select * from comment where taskId=#{taskId}  limit 0,#{n}")
    List<Comment> getComments(Integer n,Integer taskId);
//查找一个评论
    @Select("select * from comment where id=#{id}")
    Comment findById(Integer id);
}
