package com.example.demo.mapperMySQL;

import com.example.demo.bean.User;
import org.apache.ibatis.annotations.*;

/**
 * @author 将来的大拿:徐林飞
 */
@Mapper
public interface IUserMapper {
    //增
    @Insert("insert into boot_user(userName,psw) values(#{userName},#{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUser(User user);

    //按名称查询
    @Select("select * from boot_user where userName=#{userName}")
    @Results({@Result(property = "id",column = "ID"),
            @Result(property = "userName",column = "userName"),
            @Result(property = "password",column = "psw"),
            @Result(property = "userPath",column = "user_path")
    })
    User selectUserByName(String userName);

    //按照id查询用户
    @Select("select * from boot_user where id=#{id}")
    @Results({@Result(property = "id",column = "ID"),
            @Result(property = "userName",column = "userName"),
            @Result(property = "password",column = "psw"),
            @Result(property = "userPath",column = "user_path")
    })
    User selectUserById(String id);

    //更新用户数据
    @Update("<script>" +
            "update boot_user " +
            "<set>" +
            "<if test='model.userName!=null and model.userName !=\"\"'>" +
            "userName=#{model.userName},"+
            "</if>"+
            "<if test='model.password!=null and model.password !=\"\"'>" +
            "psw=#{model.password},"+
            "</if>"+
            "<if test='model.userPath!=null and model.userPath !=\"\"'>" +
            "user_path=#{model.userPath}"+
            "</if>"+
            "</set>" +
            "where id=#{model.id}"+
            "</script>")
    void update(@Param("model") User user);
}
