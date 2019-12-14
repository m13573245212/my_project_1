package com.example.demo.mapperMySQL;

import com.example.demo.bean.FileUpload;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * @author 类中域:徐林飞
 */
@Mapper
public interface IFileMapper{
    //上传主文件
    @Insert("insert into boot_file(filename,describe_file,path) values(#{fileName},#{describe_file},#{path})")
    void insert(FileUpload fileUpload);

    //删
    @Delete("delete from boot_file where filename=#{filename}")
    void delete(String filename);
    //改
    @Update("update boot_file set describe_file=#{describe_file} where filename=#{filename}")
    void updateByName(@Param("describe_file") String describe_file,
                      @Param("filename") String filename);
    //查询所有主文件
    @Select("select * from boot_file where isVideo is null")
    @Results({@Result(property = "Id", column = "ID"),
              @Result(property = "fileName", column = "filename"),
              @Result(property = "describe_file", column = "describe_file"),
              @Result(property = "path", column = "path")
    })
    List<FileUpload> selectAll();

    //按照主文件名查询主文件的ID
    @Select("select ID from boot_file where filename=#{mdName}")
    String selectMdIDByMdName(String mdName);

    //按照文件名查询一条
    @Select("select * from boot_file where filename=#{filename}")
    FileUpload selectOne(String filename);

    //查询文件描述
    @Select("select filename from boot_file where describe_file like CONCAT('%',#{describe_file},'%')")
    List<String> selectDescribe(String describe_file);

    //测试数据库能否切换
    @Insert("insert into \"File\" values(4,5,6,7)")
    void insertOracle();
}
