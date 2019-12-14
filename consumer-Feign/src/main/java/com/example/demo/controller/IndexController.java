package com.example.demo.controller;

import com.example.demo.bean.FileUpload;
import com.example.demo.bean.User;
import com.example.demo.propoclass.MdConverHtml;
import com.example.demo.service.IFileService;
import com.example.demo.util.FileUtil;
import com.example.demo.vo.ResponseResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.apache.log4j.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;
/**
 * @author 将来的大拿:徐林飞
 */
@RestController
public class IndexController {
    @Autowired
    private IFileService iFileService;

    //记录日志
    private static Logger logger = Logger.getLogger(IndexController.class);

    @RequestMapping(value = {"","/index.do","index"},method = RequestMethod.GET)
    public ModelAndView one(ModelAndView mav){
        mav.setViewName("one");
        return mav;
    }

    //查询描述功能
    @PostMapping("/search.do")
    public ResponseResult<List<String>> search(String search_text){
        ResponseResult<List<String>> rr=new ResponseResult<>();
        try{
            List<String> filesName=iFileService.selectDescribe(search_text);
            rr.setState(1);
            rr.setData(filesName);
            return rr;
        }catch(Exception e){
            rr.setState(0);
            String[] s=new String[]{"出现错误"};
            rr.setMessage(s);
            return rr;
        }
    }

    /*
    把FileUpload对象转化为String[],便于json解析
     */
    @GetMapping("/list.do")
    public ResponseResult<List<String[]>> listStart(HttpSession session){
        ResponseResult<List<String[]>> rr=new ResponseResult<>();
        //区别用户是否登录
        User user=(User)session.getAttribute("user");
        if(user==null){
            rr.setState(0);
        }else{
            rr.setState(1);
        }

        List<FileUpload> fileUploads=iFileService.selectAll();
        List<String[]> files=new ArrayList<>();
        for (FileUpload f: fileUploads) {
            String[] s=new String[]{
                    f.getId().toString(),
                    f.getFileName(),
                    f.getDescribe_file(),
                    f.getPath()
            };
            files.add(s);
        }
        rr.setData(files);
        return rr;
    }

    //用户查看文件
    @GetMapping("/look.do")
    public void lookFile(String fileName,
                         HttpServletRequest request,
                         HttpServletResponse response,
                         HttpSession session
                         ){

        String filePath ="D:/JAVA/Java相关/upload/md/"+fileName+"/"+fileName+".md";
        //把查看的文件名放进session,响应图片请求时可以用
        session.setAttribute("filePath",filePath);
        PrintWriter html_out;
        try {
            /*
            把html文件使用输出流展示
             */
            String html=MdConverHtml.mdToHtml(filePath);
            /*
             * 把"老徐最帅"替换成需要下载的文件的文件名
             */
            html=html.replaceAll("老徐最帅",fileName);
            /*
             * 需要检查是否含有file:///,有的话需要将其替换为可用的url
             */
            html=MdConverHtml.removeFileString(html,request);
            response.setContentType("text/html;charset=utf-8");
            html_out=response.getWriter();
            html_out.write(html);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //用户查看文件,使用新的逻辑,文件路径改为动态
    @GetMapping("/look2.do")
    public void lookFile2(String filePath,
                         HttpServletRequest request,
                         HttpServletResponse response,
                         HttpSession session
    ){

        if(StringUtils.isEmpty(filePath)){
            filePath="D:/JAVA/Java相关/表情包/packages.md";
        }

        //把查看的文件放进session,响应图片请求时可以用
        session.setAttribute("filePath",filePath);
        if(filePath.length()>2){
            if(".md".equals(filePath.substring(filePath.length()-3))){
                //如果文件是md格式
                PrintWriter html_out;
                try {
            /*
            把html文件使用输出流展示
             */
                    String html=MdConverHtml.mdToHtml(filePath);
                    /*
                     * 去掉下载功能
                     */
                    html=html.replaceAll("下载该文件","");

                    /*
                     * 需要检查是否含有file:///,有的话需要将其替换为可用的url
                     */
                    html=MdConverHtml.removeFileString(html,request);
                    response.setContentType("text/html;charset=utf-8");
                    html_out=response.getWriter();
                    html_out.write(html);
                } catch (IOException e){
                    e.printStackTrace();
                    logger.info("面板页面加载错误:"+e.getMessage());
                }
            }else if(".png".equals(filePath.substring(filePath.length()-4))){
                //如果文件是png图片
                try {
                    request.getRequestDispatcher(filePath.substring(filePath.lastIndexOf("/"))).forward(request, response);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping("/newMd")
    public void lookNewMdFile(
            String filePath,
            HttpSession session,
            HttpServletResponse response
    ){
        PrintWriter html_out;
        try {
            session.setAttribute("filePath",filePath);
            /*
            把html文件使用输出流展示
             */
            String html=MdConverHtml.mdToHtml(filePath);
            response.setContentType("text/html;charset=utf-8");
            html_out=response.getWriter();
            html_out.write(html);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //显示文件描述
    @GetMapping("/showDescribe.do")
    public ResponseResult<Void> showDescribe(String fileName,ModelAndView mav){
        System.out.println("查询描述的文件名:"+fileName);
        //响应页面的文件描述
        try{
            FileUpload fileUpload=iFileService.selectOne(fileName);
            ResponseResult<Void> rr=new ResponseResult<>();
            String[] s=new String[]{fileUpload.getDescribe_file()};
            rr.setMessage(s);
            return rr;
        }catch(Exception e){
            System.out.println("查询描述失败");
            return null;
        }
    }

    //修改文件描述所需要的方法
    @PostMapping("/updateDescribe.do")
    public ResponseResult<Void> updateDescribe(HttpServletRequest request){
        try{
            String describe=request.getParameter("describe_file");
            String fileName=request.getParameter("fileName");
            System.out.println(describe+"--------"+fileName);
            iFileService.updateByName(describe,fileName);
            ResponseResult<Void> rr=new ResponseResult<>();
            rr.setState(1);
            return rr;
        }catch(Exception e){
            System.out.println("更改失败");
            ResponseResult<Void> rr=new ResponseResult<>();
            rr.setState(0);
            return rr;
        }
    }

    //响应客户端的图片请求
    @GetMapping(value = "*.png",produces = "image/png")
    @ResponseBody
    public byte[] pictureSend(HttpSession session, HttpServletRequest request){

        String filePath=(String)session.getAttribute("filePath");
        //截取这个路径去掉后面的md文件
        filePath=filePath.substring(0,filePath.lastIndexOf("/")+1);
        //获取图片名
        String s=request.getRequestURI();
        String pictureName=s.substring(s.lastIndexOf("/")+1);
        System.out.println("请求的图片名:"+pictureName);
        //设置图片路径
        String picturePath=filePath+pictureName;

        //读取图片并加载到响应中,由于已经声明了produces,不需要手动设置响应头了
        try {
            byte[] pictureByte=FileUtil.readFile(picturePath);
            return pictureByte;
        } catch (IOException e) {
            System.out.println("图片读取失败");
            return null;
        }

    }

    //md文件上传
    @RequestMapping(value = "/upload.do",method = RequestMethod.POST)
    public ResponseResult<Void> uploadMd(@RequestParam("file")MultipartFile file){
        System.out.println("有文件上传");
        ResponseResult<Void> rr=new ResponseResult<>();

        //获取文件名
        String fileName=file.getOriginalFilename();
        System.out.println("上传文件的文件名:"+fileName);

        logger.info("上传文件的文件名:"+fileName);

        //获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("文件后缀名:"+suffixName);
        //设置文件的保存路径,上传文件创建在该目录下
        String filePath ="D:/JAVA/Java相关/upload/md/";
        //把文件去掉后缀名,作为该次上传的文件夹
        String fileChild=fileName.substring(0,fileName.lastIndexOf("."));
        /*
        创建目录
         */
        //指定要创建的文件夹,如果没有则创建
        File folder=new File(filePath+fileChild);
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
                File dest=new File(filePath+fileChild+"/"+fileName);
                if(dest.exists()){
                    rr.setState(0);
                    stringInformation=new String[]{"同名文件已经存在"};
                    rr.setMessage(stringInformation);
                    return rr;
                }
                try {
                    file.transferTo(dest);
                    FileUpload fileUpload=new FileUpload();
                    //保存到数据库时去掉文件后缀
                    fileUpload.setFileName(fileChild);
                    fileUpload.setDescribe_file("数据未描述");
                    fileUpload.setPath(filePath+fileChild+"/"+fileName);
                    System.out.println("显示："+filePath+fileChild+"/"+fileName);
                    iFileService.insert(fileUpload);
                    rr.setState(1);
                    stringInformation=new String[]{"文件上传成功"};
                    rr.setMessage(stringInformation);
                    return rr;
                } catch (IOException e) {
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

    //文件下载
    @GetMapping("/download.do")
    public void downloadFile(HttpServletResponse response,String fileName){
        //找到要下载的文件所在的文件夹
        String path="D:/JAVA/Java相关/upload/md/"+fileName;
        //压缩
        //暂时将它压缩在 D:/JAVA/Java相关/upload/zip/
        File zip = new File("D:/JAVA/Java相关/upload/zip/"+fileName+".zip");
        try {
            if(!zip.exists()) {
                zip.createNewFile();
            }
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip));
            List<String> needZips=new ArrayList<>();
            needZips.add(path);
            FileUtil.zipFile(needZips,zos);
            zos.close();
            String zipPath="D:/JAVA/Java相关/upload/zip/"+fileName+".zip";
            FileUtil.downloadFile(zipPath,response);
            File z=new File(zipPath);
            z.delete();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //删除主要文件
    @GetMapping("/delete.do")
    public void deleteByName(String filename){
        iFileService.delete(filename);
    }


}
