package com.finals.handy.mapper;

import com.finals.handy.bean.Report;
import org.apache.ibatis.annotations.*;

/**
 * @author xiaoqiang
 * @date $(DATE)-$(TIME)
 */
@Mapper
public interface ReportMapper {
    //    增
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into report(reason,time,reportId) values(#{reason},#{time},#{reportId})")
    boolean addReport(Report report);

    //    删
    @Delete("delete from report where id=#{id}")
    boolean deleteReport(Integer id);

//    查
    @Select("select * from report where reportId=#{id}")
    Report findReportByUId(Integer id);

    @Select("select  * from report where id=#{id}")
    Report findReportById(Integer id);
}
