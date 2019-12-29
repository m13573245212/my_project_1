package com.example.demo.mapperOracle;

import org.apache.ibatis.annotations.*;

/**
 * @author 类中域:徐林飞
 */
@Mapper
public interface NIFileMapper {

    //测试数据库能否切换
    @Insert("insert into \"File\" values(4,5,6,7)")
    void insertOracle();
}
