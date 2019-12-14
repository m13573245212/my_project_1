package com.example.demo.controller;

import com.example.demo.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 类中域:徐林飞
 * @date 2019/9/16 03:24
 */
@RestController
public class RedisController {
    @Resource
    RedisTemplate<String, User> redisTemplate;
    @RequestMapping("/redis")
    public String getRedis(){

        User user=new User();
        user.setId(1).setPassword("12345").setUserName("测试1").setUserPath("哈哈哈");
        redisTemplate.opsForValue().set("user",user);
        return "启动";
    }
}
