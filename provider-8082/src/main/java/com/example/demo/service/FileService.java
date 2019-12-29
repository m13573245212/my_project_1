package com.example.demo.service;

import com.example.demo.bean.FileUpload;
import com.example.demo.mapperMySQL.IFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author 类中域:徐林飞
 */
@Service
public class FileService implements IFileService {
    @Autowired
    private IFileMapper iFileMapper;

    //插入file数据
    @Override
    public void insert(FileUpload fileUpload) {
        iFileMapper.insert(fileUpload);
        System.out.println("插入数据成功");
    }

    //按照文件名删除
    @Override
    public void delete(String filename) {
        iFileMapper.delete(filename);
        System.out.println("删除数据成功");
    }

    //按照文件名更改
    @Override
    public void updateByName(String describe_file, String filename) {
        iFileMapper.updateByName(describe_file,filename);
        System.out.println("更改成功");
    }

    //查询所有
    @Override
    public List<FileUpload> selectAll() {
        List<FileUpload> fileUploads=iFileMapper.selectAll();
        return fileUploads;
    }

    //按照用户名查询一条
    @Override
    public FileUpload selectOne(String filename) {
        FileUpload fileUpload=iFileMapper.selectOne(filename);
        return fileUpload;
    }

    //按照主文件名查询主文件的编号
    @Override
    public String selectMdIDByMdName(String mdName) {
        String mdID=iFileMapper.selectMdIDByMdName(mdName);
        return mdID;
    }

    //查询描述
    @Override
    public List<String> selectDescribe(String describe_file) {
        List<String> filesName=iFileMapper.selectDescribe(describe_file);
        return filesName;
    }

}
