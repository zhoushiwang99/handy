package com.finals.handy.mapper;

import com.finals.handy.bean.Task;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author xiaoqiang
 * @date $(DATE)-$(TIME)
 */
@Mapper
public interface TaskMapper {


    //    增
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into task(name,content,isUse,isReport,publishId,isFinish,time,acceptId) values(#{name},#{content},0,0,#{publishId},0,#{time},0)")
    boolean addTask(Task task);

    //    有人接受了任务
    @Update("update task set isUse=1,acceptId=#{acceptId} where id=#{id}")
    boolean acceptTask(Integer id,Integer acceptId);

    //    查一个 未完成的任务
    @Select("select * from task where id=#{id} and isFinish=0 order by id desc")
    Task findTaskById(Integer id);

    //   查找有多少个未完成的任务
    @Select("select count(*) from task where isFinish=0")
    Integer findCounts();

    //    查找n个的未完成的任务
    @Select("select * from task where isFinish=0 order by id desc limit 0,#{n}")
    List<Task> findTasks(Integer n);

    //    改
    @Update("update task set name=#{name},content=#{content} where id=#{id}")
    boolean updateTask(Task task);

//    举报任务
    @Update("update task set isReport=#{reportId} where id=#{id}")
    boolean reportTask(Integer id,Integer reportId);

    //    彻底删除任务
    @Delete("delete from task where id=#{id}")
    boolean deleteTask(Integer id);

    //    完成任务
    @Update("update task set isFinish=1 where id=#{id}")
    boolean finishTask(Integer id);


//    取消任务，放弃
    @Update("update task set isUse=0, acceptId=0 where id=#{id}")
    boolean cancelTask(Integer id);
//      取消举报任务
    @Update("update task set isReport=0 where id=#{taskId}")
    boolean cancelReportTask(Integer taskId);
}
