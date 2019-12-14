package com.example.demo.controller;

import com.example.demo.feignService.MyProviderRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author 类中域:徐林飞
 */
@RestController
public class RestFileController {
    //按照服务名进行调用
    private static final String REST_URL_PREFIX="http://MYPROVIDER";

    @Autowired
    private RestTemplate restTemplate;

    //使用Feign进行调用.
    @Autowired
    private MyProviderRestService myProviderRestService;
    @RequestMapping("/testRest2")
    public Object test2(){
        return myProviderRestService.getService();
    }

    @RequestMapping("/testRest")
    public Object test(){
        return restTemplate.getForObject(REST_URL_PREFIX+"/file/discovery",Object.class);
    }

}
