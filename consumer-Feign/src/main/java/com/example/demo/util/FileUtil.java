package com.example.demo.util;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 读取文件的工具类
 * @author 将来的大拿:徐林飞
 */
public class FileUtil {
    //复制文件到指定位置
    public static void copy(File aimFile,File sourceFile) throws IOException{
        FileInputStream in = new FileInputStream(sourceFile);
        FileOutputStream out=new FileOutputStream(aimFile);
        byte[] b=new byte[2*1024*1024];
        int num;
        while((num=in.read(b))!=-1){
            out.write(b,0,num);
        }
        in.close();
        out.close();
        System.out.println("复制文件成功");
    }


    //读取文件以String形式返回,带缓冲
    public static String readAll(String filePath) throws IOException {

        InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "UTF-8");
        BufferedReader reader = new BufferedReader(isr);
        String s;
        StringBuilder out=new StringBuilder();
        while((s=reader.readLine())!=null){
            out.append(s);
            out.append('\n');
        }
        isr.close();
        reader.close();
        return out.toString();
    }

    //一次性加载文件所有内容,以byte[]数组返回
    public static byte[] readFile(String path) throws IOException {
        File file=new File(path);
        byte[] bytes= new byte[(int)file.length()];
        FileInputStream in= new FileInputStream(file);
        in.read(bytes);
        in.close();
        return bytes;
    }

    //文件压缩
    /**
     * 压缩文件
     * @param filePaths 需要压缩的文件路径集合
     * @throws IOException
     */
    public static void zipFile(List<String> filePaths, ZipOutputStream zos) throws IOException {

        //循环读取文件路径集合，获取每一个文件的路径
        for(String filePath : filePaths){
            File inputFile = new File(filePath);  //根据文件路径创建文件
            if(inputFile.exists()) { //判断文件是否存在
                if (inputFile.isFile()) {  //判断是否属于文件，还是文件夹
                    //创建输入流读取文件
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputFile));

                    //将文件写入zip内，即将文件进行打包
                    zos.putNextEntry(new ZipEntry(inputFile.getName()));

                    //写入文件的方法，同上
                    int size = 0;
                    byte[] buffer = new byte[1024];  //设置读取数据缓存大小
                    while ((size = bis.read(buffer)) > 0) {
                        zos.write(buffer, 0, size);
                    }
                    //关闭输入输出流
                    zos.closeEntry();
                    bis.close();

                } else {  //如果是文件夹，则使用穷举的方法获取文件，写入zip
                    try {
                        File[] files = inputFile.listFiles();
                        List<String> filePathsTem = new ArrayList<String>();
                        for (File fileTem:files) {
                            filePathsTem.add(fileTem.toString());
                        }
                        zipFile(filePathsTem,zos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //文件下载
    public static void downloadFile(String path, HttpServletResponse response){
        try {
            File file=new File(path);
            String fileTrue=file.getName();

            //进行特殊字符转化
            fileTrue = URLEncoder.encode(fileTrue, "UTF-8").replaceAll("%20", "\\+").replaceAll("%28", "\\(")
                    .replaceAll("%29", "\\)").replaceAll("%3B", ";").replaceAll("%40", "@").replaceAll("%23", "\\#")
                    .replaceAll("%26", "\\&").replaceAll("%2C", "\\,").replaceAll("%24", "\\$")
                    .replaceAll("%25", "\\%").replaceAll("%5E", "\\^").replaceAll("%3D", "\\=")
                    .replaceAll("%2B", "\\+");

            FileInputStream in=new FileInputStream(path);
            OutputStream out=response.getOutputStream();
            byte[] buffer=new byte[1024];
            int len=0;
            while((len=in.read(buffer))!=-1){
                out.write(buffer,0,len);
            }
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + fileTrue);
            response.addHeader("Content-Length", "" + file.length());
//            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //删除文件
    public static void deleteFile(String path) throws FileNotFoundException {
        File file=new File(path);
        if(file.exists()){
            file.delete();
        }else{
            throw new FileNotFoundException();
        }
    }

    /**
     * 通过路径找到对应的文件或者目录，加载该目录下的文件结构，拼装成json字符串
     * 需要注意的是:这个方法处理的json外部还需要使用中括号包裹，这个中括号不能放在参数里。
     * @param file
     * @param res
     * @return
     */
    public static String treeJson(File file,String res) throws Exception{
        try{
            if(file.exists()){
                res+="{text:\""+file.getName()+"\"";
                if(file.isDirectory()){
                    //如果是目录，列举目录下所有的File
                    File[] fs=file.listFiles();
                    if(fs.length>0){
                        res+=",nodes:[";
                        for (int i=0;i<fs.length;i++) {
                            //遍历每一个File对象
                            if(i==fs.length-1){
                                if(fs[i].isDirectory()){
                                    res=treeJson(fs[i],res);
                                }else{
                                    res+="{text:\""+fs[i].getName()+"\"}";
                                }

                            }else{
                                if(fs[i].isDirectory()){
                                    res=treeJson(fs[i],res);
                                    res+=",";
                                }else{
                                    res+="{text:\""+fs[i].getName()+"\"},";
                                }
                            }
                        }
                        res+="]}";
                    }else{
                        res+="}";
                    }
                }else{
                    //文件肯定没有下级节点，拼接符号完成该节点的终止
                    res+="}";
                }
            }else{
                //路径下文件不存在，此时不应该拼接任何符号
                res+="";
            }
            return res;
        }catch(Exception e){
            throw new Exception("解析路径失败");
        }
    }
}
