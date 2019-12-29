package com.example.demo.mapperMySQL;

import com.example.demo.bean.Picture;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author 将来的大拿:徐林飞
 */
@Mapper
public interface IPicturesMapper {

    //上传图片
    @Insert("insert into boot_pictures(picture_name,md_master)values(#{picture_name},#{md_master})")
    void insertPictures(Picture picture);

    //按照主文件ID查询属于该文件的图片
    @Select("select * from boot_pictures where md_master=#{mdID}")
    @Results({@Result(property = "ID", column = "ID"),
            @Result(property = "picture_name", column = "picture_name"),
            @Result(property = "md_master", column = "md_master"),
    })
    List<Picture> selectPicturesOfMd(String mdID);

    //删除图片
    @Delete("delete from boot_pictures where picture_name=#{picture_name} and md_master=#{md_master}")
    void deletePictureByIDAndName(@Param("picture_name") String picture_name, @Param("md_master") String md_master);
}
