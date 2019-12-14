package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 类中域:徐林飞
 * @date 2019/12/9 09:02
 */
@RestController
public class JsonpTest {
    @RequestMapping(value = {"/testVue"},method = RequestMethod.GET)
    public String one(){

        return "成功！";
    }

    @RequestMapping(value = {"/testJsonp"},method = RequestMethod.GET,produces="application/x-javascript;charset=UTF-8")
    @ResponseBody
    public String one(String callback){
        return callback+"({'d':'1'})";
    }
}
