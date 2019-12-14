package com.example.demo.controller;

import com.example.demo.bean.FileUpload;
import com.example.demo.bean.User;
import com.example.demo.feignService.MyProviderRestService;
import com.example.demo.service.IFileService;
import com.example.demo.util.FileUtil;
import com.example.demo.vo.ResponseResult;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注入
 *
 * @author 类中域:徐林飞
 */
@Controller
@RequestMapping("/manage")
//该注解生成的日志对象名称默认为log
@Log4j
public class ManageController {

    @Autowired
    private MyProviderRestService myProviderRestService;
    @Autowired
    private IFileService iFileService;

    //返回管理控制页面
    @RequestMapping("")
    public ModelAndView getManagePage(ModelAndView mav, HttpSession session) {
        String tree1 = "";
        String tree2 = "";
        //需要查找用户的默认路径,没有的话默认使用 D:/JAVA/Java相关/upload/md
        User user = (User) session.getAttribute("user");
        String path = "";
        if (null == user) {
            path = "D:/JAVA/Java相关/upload/md";
        } else {
            path = user.getUserPath();
        }
        File mdDir = new File(path);
        try {
            tree2 = "[" + FileUtil.treeJson(mdDir, tree1) + "]";
            System.out.println("tree字符串:" + tree2);
            mav.addObject("data", tree2);
            mav.addObject("path", path);
            mav.setViewName("main_home/manageHtml");
            return mav;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("tree加载失败");
            return null;
        }
    }

    //进行预览时，加载基础页面
    @RequestMapping("/wordHtml")
    public ModelAndView getWordHtmlPage(ModelAndView mav, String filePath) {
        mav.addObject("filePath", filePath);
        mav.setViewName("main_home/wordHtml");
        return mav;
    }

    //加载新的路径
    @RequestMapping("/newDir.do")
    @ResponseBody
    public String getNewDir(String path, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (null != user) {
            //用户登陆的话需要将原有的默认加载路径进行替换
            user.setUserPath(path);
            myProviderRestService.update(user);
        }
        //生成路径下的tree字符串
        String tree1 = "";
        String tree2 = "";
        try {
            File mdDir = new File(path);
            tree2 = "[" + FileUtil.treeJson(mdDir, tree1) + "]";
        } catch (Exception e) {
            e.printStackTrace();
            log.info("生成新的tree时错误:"+e.getMessage());
        }
        return tree2;
    }

    //管理页面的文件上传
    @RequestMapping("/upload")
    @ResponseBody
    public ResponseResult<Void> upload(String filePath){
        ResponseResult<Void> rr=new ResponseResult<>();
        //获取文件名
        String fileName=filePath.substring(filePath.lastIndexOf("/")+1);
        log.info("上传文件的文件名:"+fileName);

        //获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //设置文件的保存路径,上传文件创建在该目录下
        String needFilePath ="D:/JAVA/Java相关/upload/md/";
        //把文件去掉后缀名,作为该次上传的文件夹
        String fileChild=fileName.substring(0,fileName.lastIndexOf("."));
        /*
        创建目录
         */
        //指定要创建的文件夹,如果没有则创建
        File folder=new File(needFilePath+fileChild);
        //生成信息返回页面提醒用户
        String[] stringInformation;
        if(folder.exists()){
            rr.setState(0);
            stringInformation=new String[]{"同名文件已经存在"};
            rr.setMessage(stringInformation);
            return rr;
        }else{
            try {
                folder.mkdirs();
                File dest=new File(needFilePath+fileChild+"/"+fileName);
                if(dest.exists()){
                    stringInformation=new String[]{"同名文件已经存在"};
                    rr.setMessage(stringInformation);
                    return rr;
                }
                try {
                    File file=new File(filePath);
                    //复制文件到指定的位置
                    FileUtil.copy(dest,file);
                    FileUpload fileUpload=new FileUpload();
                    //保存到数据库时去掉文件后缀
                    fileUpload.setFileName(fileChild);
                    fileUpload.setDescribe_file("数据未描述");
                    fileUpload.setPath(needFilePath+fileChild+"/"+fileName);
                    iFileService.insert(fileUpload);
                    stringInformation=new String[]{"文件上传成功"};
                    rr.setMessage(stringInformation);
                    return rr;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                rr.setState(0);
                stringInformation=new String[]{"文件上传失败"};
                rr.setMessage(stringInformation);
                return rr;
            } catch (Exception e) {
                System.out.println("上传文件创建文件夹失败");
                rr.setState(0);
                stringInformation=new String[]{"文件上传失败"};
                rr.setMessage(stringInformation);
                return rr;
            }
        }
    }

    //需要自动上传图片
    @RequestMapping("/autoUploadPictures")
    @ResponseBody
    public ResponseResult<Map<String,List<String>>> autoUploadPictures(String filePath){
        ResponseResult<Map<String,List<String>>> rr=new ResponseResult<>();
        //截取文件名
        String fileName=filePath.substring(filePath.lastIndexOf("/")+1);
        //查找目的地文件夹已经存在的图片
        String airFilePath="D:/JAVA/Java相关/upload/md/"+fileName.substring(0,fileName.lastIndexOf("."));
        File airFile=new File(airFilePath);

        List<String> list=new ArrayList<>();
        if(airFile.exists()&&airFile.isDirectory()){
            File[] fs=airFile.listFiles();
            for (File f : fs) {
                list.add(f.getName());
            }
        }else{
            rr.setState(0);
            rr.setMessage(new String[]{"未发现主文件目录,请确定是否已经上传主文件"});
            return rr;
        }

        //截取文件所在的文件夹
        filePath=filePath.substring(0,filePath.lastIndexOf("/"));

        File file=new File(filePath);
        if(file.exists()){
            //列举(源)所有文件
            File[] files=file.listFiles();

            //用于存放上传的文件名
            List<String> s1=new ArrayList<>();
            //用于存放同名文件或失败的文件名
            List<String> s2=new ArrayList<>();

            for (File f : files) {
                if(f.isFile()&&f.getName().indexOf(".png")!=-1){
                    //防止存在同名图片
                    if(!list.contains(f.getName())){
                        //copy
                        try {
                            File airFileSon=new File(airFilePath+"/"+f.getName());
                            FileUtil.copy(airFileSon,f);
                            s1.add(f.getName());
                        } catch (IOException e) {
                            s2.add(f.getName());
                        }
                    }else{
                        s2.add(f.getName());
                    }
                }
            }
            rr.setState(1);
            rr.setMessage(new String[]{"文件上传成功"});
            Map<String,List<String>> m=new HashMap<>();
            m.put("success",s1);
            m.put("fail",s2);
            rr.setData(m);
            return rr;
        }else{
            System.out.println("自动上传图片,文件路径解析有误");
            rr.setState(0);
            rr.setMessage(new String[]{"文件源有误"});
            return rr;
        }
    }
}
