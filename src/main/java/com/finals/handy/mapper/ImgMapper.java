package com.finals.handy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaoqiang
 * @date $(DATE)-$(TIME)
 */
@Mapper
public interface ImgMapper {

    //    增
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into img(otherId,imgPath) values(#{taskId},#{imgPath})")
    boolean addImgPath(Integer taskId, String imgPath);


//    删

    //    查
    @Select("select imgPath from img where otherId=#{id}")
    List<String> getImgPath(Integer id);

}
