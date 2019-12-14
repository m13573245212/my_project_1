package com.example.demo.controller;

import com.example.demo.bean.Node;
import com.example.demo.bean.User;
import com.example.demo.feignService.MyProviderRestService;
import com.example.demo.util.NodeToString;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 展示
 * @author 类中域:徐林飞
 */
@RestController
@Log4j
@RequestMapping("/zhan")
public class ZhanController {
    @Autowired
    private MyProviderRestService myProviderRestService;
    @RequestMapping("")
    public ModelAndView getPage(ModelAndView modelAndView){
        modelAndView.setViewName("main_home/specialHtml");
        return modelAndView;
    }

    @RequestMapping("/isLogin")
    @ResponseBody
    public String getLogin(HttpSession session){
        User user=(User)session.getAttribute("user");
        if(null==user){
            return "0";
        }else{
            return "1";
        }
    }

    @RequestMapping("/tree")
    @ResponseBody
    public String getTree(HttpSession session){
        String data="";
        //查询这个人的节点
        User user=(User)session.getAttribute("user");
        List<Node> nodes=myProviderRestService.selectListNodes(user);
        if(null==nodes){
            nodes=new ArrayList<>();
        }else{
            if(nodes.size()>0){
                data= NodeToString.getDataForZhan(nodes);
            }
        }
        return data;
    }
}
