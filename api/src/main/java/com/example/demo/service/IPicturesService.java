package com.example.demo.service;

import com.example.demo.bean.Picture;

import java.util.List;

/**
 * @author 将来的大拿:徐林飞
 */
public interface IPicturesService {
    //插入新图片数据
    void insert(Picture picture);

    //查询一个主文件下的所有图片
    List<Picture> selectPicturesOfMd(String mdID);

    //通过图片名和主文件ID删除单张图片
    void deleteOne(String picture_name, String md_master);
}
