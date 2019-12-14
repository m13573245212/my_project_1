package com.example.demo.controller;

import com.example.demo.vo.ResponseResult;
import com.example.demo.bean.FileUpload;
import com.example.demo.bean.Picture;
import com.example.demo.bean.User;
import com.example.demo.service.IFileService;
import com.example.demo.service.IPicturesService;
import com.example.demo.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 将来的大拿:徐林飞
 */
@RestController
public class PicturesController {
    @Autowired
    private IPicturesService iPicturesService;
    @Autowired
    private IFileService iFileService;

    //图片文件上传
    @PostMapping("/uploadPictures.do")
    public ResponseResult<Void> uploadPictures(MultipartFile[] pictures, String master){
        System.out.println("有图片上传");
        ResponseResult<Void> rr=new ResponseResult<>();

        //生成信息返回页面提醒用户
        //每一个文件对应一个结果放在数组中,后续在页面显示
        String[] stringInformation=new String[pictures.length];

        /*
         * 上传的从属文件,放在主文件所在目录中
         */
        String filePath="";
        if(master.contains(".")){
            master=master.substring(0,master.lastIndexOf("."));
            filePath ="D:/JAVA/Java相关/upload/md/"+master;
        }else{
            filePath ="D:/JAVA/Java相关/upload/md/"+master;
        }
        System.out.println("解析出来的主文件路径:"+filePath);

        File folder=new File(filePath);
        //查看主文件是否存在,如果没有则提示用户主文件不存在
        if(!folder.exists()){
            stringInformation[0]="主文件不存在,拒绝上传";
            rr.setMessage(stringInformation);
            return rr;
        }else{
            //如果文件存在,需要得到该主文件的ID,因为数据库需要确定图片所属主文件的ID,可以由主文件名确定ID.
            //master已经经过处理,去掉了有可能出现的后缀问题
            String mdID=iFileService.selectMdIDByMdName(master);

            for(int i=0;i<pictures.length;i++) {
                MultipartFile file_picture=pictures[i];
                //获取文件名
                String fileName = file_picture.getOriginalFilename();
                System.out.println("上传图片的文件名:" + fileName);
                //获取文件的后缀名
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                System.out.println("图片后缀名:" + suffixName);

                try {
                    File dest=new File(filePath+"/"+fileName);
                    if(dest.exists()){
                        stringInformation[i]="与"+fileName+"同名的图片已经存在,该图片拒绝";
                    }else{
                        //如果图片不存在,接受图片
                        try {
                            file_picture.transferTo(dest);
                            //当确定图片可以上传时,记录数据到数据库相应的表中
                            Picture picture=new Picture();
                            picture.setMd_master(Integer.valueOf(mdID));
                            picture.setPicture_name(fileName);
                            iPicturesService.insert(picture);

                            stringInformation[i]=fileName+",图片上传成功";
                        } catch (IOException e) {
                            stringInformation[i]="发生错误,"+fileName+"图片上传失败";
                        }
                    }
                } catch (Exception e) {
                    stringInformation[i]="发生错误,"+fileName+"图片上传失败";
                }
            }
        }
        rr.setMessage(stringInformation);
        return rr;
    }

    //动态表格
    @PostMapping("/table.do")
    @ResponseBody
    public ResponseResult<List<HashMap>> table(){
        List<HashMap> results=new ArrayList<>();

        //得到所有的主文件
        List<FileUpload> fileUploads=iFileService.selectAll();
        //编号
        int i=1;

        for (FileUpload fileUpload: fileUploads) {
            Integer id=fileUpload.getId();
            //按照ID查询,属于该文件的图片
            List<Picture> pictures=iPicturesService.selectPicturesOfMd(id.toString());

            HashMap row=new HashMap();
            //统计数量
            int sum=pictures.size();
            //主文件名
            String master=fileUpload.getFileName();
            row.put("mainFile",master);
            row.put("describe",fileUpload.getDescribe_file());
            row.put("sum",sum+"张");
            row.put("number",i);
            System.out.println(row);
            results.add(row);
            i++;
        }

        ResponseResult<List<HashMap>> rr=new ResponseResult<>();
        rr.setData(results);
        return rr;
    }

    //图片集模态数据设置
    @PostMapping("/pictures_model.do")
    public ResponseResult<List<HashMap>> selectPicturesForModel(String masterName){
        ResponseResult<List<HashMap>> rr=new ResponseResult<>();
        String mdID=iFileService.selectMdIDByMdName(masterName);
        List<Picture> pictures=iPicturesService.selectPicturesOfMd(mdID);

        List<HashMap> rows=new ArrayList<>();
        int i=1;
        for (Picture picture:pictures) {
            HashMap row=new HashMap();
            row.put("number",i);
            row.put("pictureName",picture.getPicture_name());
            i++;
            rows.add(row);
        }

        rr.setData(rows);
        return rr;
    }

    //显示选中的单张图片
    @GetMapping("/lookPicture.do")
    public void lookPicture(String pName, String master, HttpServletResponse response) throws UnsupportedEncodingException {
        master= URLDecoder.decode(master,"utf-8");
        //不需要查询数据库
        String path="D:/JAVA/Java相关/upload/md/"+master+"/"+pName;
        try {
            byte[] b=FileUtil.readFile(path);
            response.setContentType("image/png");
            OutputStream out=response.getOutputStream();
            out.write(b);
        } catch (IOException e) {
            System.out.println("读取图片失败");
        }
    }

    //删除单张图片
    @GetMapping("/deletePicture.do")
    @ResponseBody
    public ResponseResult<Void> deletePicture(String pName, String master, HttpSession session){
        ResponseResult<Void> rr=new ResponseResult<>();
        //删除时需要验证是否登录,没有登录禁止删除
        User user=(User)session.getAttribute("user");
        if(user==null){
            String[] s=new String[]{"需要登录才能进行该操作"};
            rr.setMessage(s);
            rr.setState(0);
            return rr;
        }else{
            String mdID=iFileService.selectMdIDByMdName(master);
            try{
                //删除数据库记录
                iPicturesService.deleteOne(pName,mdID);
            }catch(Exception e){
                String[] s=new String[]{"删除失败"};
                rr.setMessage(s);
                rr.setState(0);
                return rr;
            }
            //删除实体文件
            String path="D:/JAVA/Java相关/upload/md/"+master+"/"+pName;
            try {
                FileUtil.deleteFile(path);
            } catch (FileNotFoundException e) {
                String[] s=new String[]{"未找到该文件"};
                rr.setMessage(s);
                rr.setState(0);
                return rr;
            }
            String[] s=new String[]{"删除成功"};
            rr.setMessage(s);
            rr.setState(1);
            return rr;
        }
    }
}
