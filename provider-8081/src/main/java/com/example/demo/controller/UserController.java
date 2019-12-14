package com.example.demo.controller;

import com.example.demo.bean.User;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 类中域:徐林飞
 */
@RestController
public class UserController {
    @Autowired
    private IUserService iUserService;

    //更新用户信息
    @RequestMapping("/updateUser")
    public void update(@RequestBody User user){
        iUserService.updateById(user);
    }
}
