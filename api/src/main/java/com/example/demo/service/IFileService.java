package com.example.demo.service;

import com.example.demo.bean.FileUpload;

import java.util.List;

/**
 * @author 类中域:徐林飞
 */
public interface IFileService {
    //增
    void insert(FileUpload fileUpload);
    //删
    void delete(String filename);
    //改
    void updateByName(String describe_file, String filename);
    //查所有
    List<FileUpload> selectAll();
    //按照用户名查询一条
    FileUpload selectOne(String filename);
    //按照主文件名查询主文件的编号
    String selectMdIDByMdName(String mdName);
    //查询描述
    List<String> selectDescribe(String describe_file);
}
