package com.example.demo.mapperMySQL;

import com.example.demo.bean.FileUpload;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 类中域:徐林飞
 */
@Mapper
public interface IVideoMapper {
    //查询视频文件的列表
    @Select("select * from boot_file where isVideo=1")
    @Results({@Result(property = "Id",column = "ID"),
            @Result(property = "fileName",column = "filename"),
            @Result(property = "describe_file",column = "describe_file"),
            @Result(property = "path",column = "path"),
            @Result(property = "isVideo",column = "isVideo")})
    List<FileUpload> getList();

    //通过id查询相关联的文件的路径
    @Select("select path from boot_file where ID=#{id}")
    String getOneVideo(String id);
}
