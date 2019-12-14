package com.example.demo.service;

import com.example.demo.bean.FileUpload;
import com.example.demo.mapperMySQL.IVideoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 类中域:徐林飞
 */
@Service
public class VideoService implements IVideoService{

    @Resource
    private IVideoMapper iVideoMapper;

    @Override
    public List<FileUpload> getList() {
        List<FileUpload> videos=iVideoMapper.getList();
        return videos;
    }

    @Override
    public String getOneVideo(String id) {
        String videoPath=iVideoMapper.getOneVideo(id);
        return videoPath;
    }
}
