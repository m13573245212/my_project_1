package com.example.demo.service;

import com.example.demo.bean.FileUpload;

import java.util.List;

/**
 * @author 类中域:徐林飞
 */
public interface IVideoService {
    //查询视频文件的列表
    List<FileUpload> getList();
    //通过id查询关联的文件的文件路径
    String getOneVideo(String id);

}
