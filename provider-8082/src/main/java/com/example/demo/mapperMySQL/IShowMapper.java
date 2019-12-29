package com.example.demo.mapperMySQL;

import com.example.demo.bean.Node;
import com.example.demo.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * @author 类中域:徐林飞
 */
@Mapper
public interface IShowMapper {
    @Insert("insert into tree_point("+
            "   farther_id,node_id,node_text,master_id) "+
            "values("+
            "   #{model.fartherId},"+
            "   #{model.nodeId},"+
            "   #{model.nodeText},"+
            "   #{model.masterId})")
    void addNode(@Param("model")Node node);
    @Delete("delete from tree_point where node_id=#{model.nodeId} and master_id=#{model.masterId}")
    void delNode(@Param("model")Node node);
    @Delete("delete from tree_point where farther_id=#{model.nodeId} and master_id=#{model.masterId}")
    void delNodeSon(@Param("model")Node node);
    @Update("update tree_point set node_text=#{model.nodeText} where node_id=#{model.nodeId} and master_id=#{model.masterId}")
    void updateNode(@Param("model")Node node);

    @Update("update tree_point set des=#{model.des} where node_id=#{model.nodeId} and master_id=#{model.masterId}")
    void updateNodeDes(@Param("model")Node node);

    @Select("select * from tree_point where master_id=#{model.id}")
    @Results({@Result(property = "id",column = "id"),
            @Result(property = "fartherId",column = "farther_id"),
            @Result(property = "nodeId",column = "node_id"),
            @Result(property = "nodeText",column = "node_text"),
            @Result(property = "masterId",column = "master_id"),
            @Result(property = "des",column = "des")})
    List<Node> selectListNodes(@Param("model")User user);

    @Select("select * from tree_point where node_id=#{model.nodeId} and master_id=#{model.masterId}")
    @Results({@Result(property = "id",column = "id"),
            @Result(property = "fartherId",column = "farther_id"),
            @Result(property = "nodeId",column = "node_id"),
            @Result(property = "nodeText",column = "node_text"),
            @Result(property = "masterId",column = "master_id"),
            @Result(property = "des",column = "des")})
    Node selectOneNode(@Param("model")Node node);
}
