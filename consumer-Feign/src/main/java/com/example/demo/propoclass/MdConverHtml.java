package com.example.demo.propoclass;


import com.example.demo.util.FileUtil;
import org.pegdown.PegDownProcessor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 把md格式转化成html
 * @author 类中域:徐林飞
 */
public class MdConverHtml {

    //设置为静态防止多次加载
    private static String MD_CSS;
    private static String MD_DIR;
    private static String MD_DOWN_BUTTON;

    static {
        try {
            //读取CSS样式
            MD_CSS = FileUtil.readAll("./consumer-Feign/src/main/resources/myfiles/css样式.txt ");
            MD_CSS = "<style type=\"text/css\">\n" + MD_CSS + "\n</style>\n";
            //读取生成目录的js
            MD_DIR=FileUtil.readAll("./consumer-Feign/src/main/resources/myfiles/dirJS.txt ");
            //读取生成下载按钮的js
            MD_DOWN_BUTTON=FileUtil.readAll("./consumer-Feign/src/main/resources/myfiles/downButton.txt");
        } catch (Exception e) {
            MD_CSS = "";
            MD_DIR = "";
            MD_DOWN_BUTTON="";
        }
    }
    //把md文件转化为html字符串
    public static String mdToHtml(String filePath) throws IOException {
        //读取文件以String类型返回
        String mdString=FileUtil.readAll(filePath);

        PegDownProcessor pdp=new PegDownProcessor(Integer.MAX_VALUE);

        String html=pdp.markdownToHtml(mdString);

        /**
         * 为html添加样式
         */
        return MD_CSS+MD_DIR+MD_DOWN_BUTTON+html;
    }

    //md文件中存在file:///这是为了进行页面跳转时需要的markdown语法,但是由于访问的是本地文件系统,在网页上打开时会被系统禁止,所以进行以下方法
    public static String removeFileString(String mdString, HttpServletRequest request){
        System.out.println("当前浏览器访问路径："+request.getRequestURL());
        //截取访问路径
        String url=request.getRequestURL().toString();
        int i=url.lastIndexOf("/");
        url=url.substring(0,i);
        System.out.println("截取的访问路径是："+url);
        if(mdString.contains("file:///")){
            System.out.println("测试是否经过该方法");
            mdString=mdString.replaceAll("file:///",url+"/newMd?filePath=");
            System.out.println(mdString);
            return mdString;
        }else{
            return mdString;
        }
    }


}
