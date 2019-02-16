package com.finals.handy.mapper;

import com.finals.handy.bean.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author xiaoqiang
 * @date $(DATE)-$(TIME)
 */
@Mapper
public interface MessageMapper {
    @Insert("insert into MSG(fromId,toId,from_toId,content,state,time) values(#{fromId},#{toId},#{from_toId},#{content},#{state},#{time})")
    public boolean addMessage(Message message);

    //   按接受者的id查找
    @Select("select * from MSG where toId=#{id}  and isDelete=0")
    public List<Message> findMessageBytoId(Integer id);

//    查询所有记录
    @Select("select * from MSG  and isDelete=0")
    public List<Message> findAllMessage();

    //  按发送者的id查找
    @Select("select * from MSG where fromId=#{id}  and isDelete=0")
    public List<Message> findMessageByfromId(Integer id);

    //    按发送和接受者id一起查找   已读
    @Select("select * from MSG where from_toId=#{from_toId}  and isDelete=0 and state=1 order by id desc limit 1,#{n}")
    public List<Message>findMessageByfromId_ToId(String from_toId, Integer n);



    //   查找未读的消息
    @Select("select * from MSG where fromId=#{fromId} and toId=#{toId} and state=0  and isDelete=0")
    public List<Message> findMessages_NoRead(Integer fromId, Integer toId);

    //    设置已读
    @Update("update MSG set state=1 where fromId=#{fromId} and toId=#{toId} and  state=0 and isDelete=0")
    public Integer setHaveRead(Integer fromId, Integer toId);

    //  删除记录，其实是设置isDelete=1  为已删除
    @Update("update MSG set isDelete=1 where id=#{id}")
    public boolean delete(Integer id);

    //    查找所有未读的消息  toId
    @Select("select fromId from MSG where toId=#{toId} and state=0 and isDelete=0")
    public List<String> findAllNo_read(Integer toId);

    //    查询给我发的未读消息的数量
    @Select("select count(*) from msg where toId=#{userId} and state=0 and isDelete=0")
    public Integer findMessageNum(Integer userId);

    //    查询给我发送未读消息的所有人
    @Select("select fromId from msg where toId=#{userId} and state=0 and isDelete=0")
    public List<Integer> findFromId(Integer userId);
}
