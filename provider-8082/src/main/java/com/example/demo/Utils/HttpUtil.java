package com.example.demo.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 对接乐农的http接口访问类
 * @author 类中域:徐林飞
 */
public class HttpUtil {
    //从云学习同步考试记录
    public static String getExams(String start,String end) throws Exception {

        String url = "https://learning.haier.net/open/v1/report/report/getOnlineCourseReportList.html";
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(url);
        //key value
        List<NameValuePair> params = new ArrayList<>();

        CourseReportQuery aa = new CourseReportQuery();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        aa.setEmployeeCodes(null);
        aa.setStartTime(format.parse(start));
        aa.setCourseCodes(null);
        aa.setEndTime(format.parse(end));
        com.alibaba.fastjson.JSONObject json = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.toJSON(aa);

        NameValuePair pair = new BasicNameValuePair("courseReportQuery", json.toString());
        NameValuePair pair2 = new BasicNameValuePair("appKey_", "5BC2F2270639458399CDEE93CA6F86C2");
        NameValuePair pair3 = new BasicNameValuePair("sign_", "1E5ED75C119722F263CDE67781DE17B4");
        NameValuePair pair4 = new BasicNameValuePair("timestamp_", System.currentTimeMillis() + "");

        params.add(pair);
        params.add(pair2);
        params.add(pair3);
        params.add(pair4);
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        //执行并拿到结果
        HttpResponse response = client.execute(httpPost);
        HttpEntity entity2 = response.getEntity();
        String str = EntityUtils.toString(entity2, "UTF-8");
        System.out.println(str);
        return str;
    }

    public static void main(String[] args) {
        try {
            String s=HttpUtil.getExams("2018-07-13","2018-07-23");
            System.out.printf(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
