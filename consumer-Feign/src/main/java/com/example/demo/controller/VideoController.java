package com.example.demo.controller;

import com.example.demo.bean.FileUpload;
import com.example.demo.service.IVideoService;
import com.example.demo.vo.ResponseResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.util.List;

/**
 * 视频播放相关
 * @author 类中域:徐林飞
 */
@Controller
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private IVideoService iVideoService;
    //记录日志
    private static Logger logger = Logger.getLogger(VideoController.class);

    //返回视频播放的页面
    @RequestMapping("")
    public ModelAndView getVideo(){
        return new ModelAndView("main_home/videoHtml");
    }

    //获取视频列表
    @RequestMapping("/list")
    @ResponseBody
    public ResponseResult<List<FileUpload>> getVideoList(){
        ResponseResult<List<FileUpload>> rr=new ResponseResult<>();
        try{
            //查询数据库中上传文件表中所有的视频文件(视频文件的isVideo为1)
            List<FileUpload> videos=iVideoService.getList();
            rr.setState(1);
            rr.setData(videos);
            return rr;
        }catch (Exception e){
            logger.error("获取视频列表出现错误："+e.getMessage());
            rr.setState(0);
            return rr;
        }
    }

    //播放视频需要的方法
    @RequestMapping("/getVideo")
    @ResponseBody
    public byte[] getVideo(String id){
        System.out.println("测试是否启动");
        System.out.println("id是"+id);

        //按照ID查询视频所在的地址
        String videoPath=iVideoService.getOneVideo(id);

        FileInputStream file=null;
        BufferedInputStream file_in_io=null;
        ByteArrayOutputStream file_out_io=null;
        //测试使用流，测试播放器反应
        try{
//            File file_video=new File("D:/JAVA/Java相关/upload/testVideo.mp4");
            File file_video=new File(videoPath);
            if(file_video.exists()){
                file=new FileInputStream(file_video);
                file_in_io=new BufferedInputStream(file);
                byte [] bytes_buffer = new byte[8*1024*1024];
                int len=0;

                file_out_io = new ByteArrayOutputStream();

                while((len=file_in_io.read(bytes_buffer))!=-1){
                    file_out_io.write(bytes_buffer,0,len);
                }

                return file_out_io.toByteArray();
            }else{
                return null;
            }
        }catch(IOException e){
            System.err.println("视频播放出现异常");
            return null;
        }finally {
            try{
                file.close();
                file_in_io.close();
                file_out_io.close();
            }catch (IOException e){
                e.printStackTrace();
                System.out.println("视频播放,流关闭异常");
            }
        }
    }
}
