package com.example.demo.service;

import com.example.demo.bean.Picture;
import com.example.demo.mapperMySQL.IPicturesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 将来的大拿:徐林飞
 */
@Service
public class PicturesService implements IPicturesService {
    @Autowired
    private IPicturesMapper iPicturesMapper;


    @Override
    public void insert(Picture picture) {
        iPicturesMapper.insertPictures(picture);
    }

    //查询一个主文件下所有的从属图片
    @Override
    public List<Picture> selectPicturesOfMd(String mdID) {
        List<Picture> pictures=iPicturesMapper.selectPicturesOfMd(mdID);
        return pictures;
    }

    //删除单张图片
    @Override
    public void deleteOne(String picture_name, String md_master) {
            iPicturesMapper.deletePictureByIDAndName(picture_name,md_master);
    }
}
