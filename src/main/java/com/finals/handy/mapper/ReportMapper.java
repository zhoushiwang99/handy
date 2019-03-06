package com.finals.handy.mapper;

import com.finals.handy.bean.Report;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

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
}
