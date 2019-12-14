package com.example.demo.controller;

import com.example.demo.bean.User;
import com.example.demo.service.IUserService;
import com.example.demo.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author 将来的大拿:徐林飞
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService iUserService;

    //验证用户名是否存在
    @PostMapping("/checkUserName.do")
    @ResponseBody
    public ResponseResult<Void> checkUserName(String userName){
        ResponseResult<Void> rr=new ResponseResult<>();
        User user=iUserService.selectByName(userName);
        if(user==null){
            rr.setState(0);
            String[] s={"用户名不存在"};
            rr.setMessage(s);
            return rr;
        }
        rr.setState(1);
        String[] s={"用户名已存在"};
        rr.setMessage(s);
        return rr;
    }

    //登录,验证密码
    @PostMapping("/login.do")
    @ResponseBody
    public ResponseResult<Void> login(String userName, String password, HttpSession session){
        System.out.println("有用户登录");
        ResponseResult<Void> rr=new ResponseResult<>();
        User user=iUserService.selectByName(userName);
        //由于已经验证了用户名是否存在,所以此时按照用户名查询是一定可以查找到数据的
        if(user.getPassword().equals(password)){
            rr.setState(1);
            String[] s={"登录成功"};
            session.setAttribute("user",user);
            rr.setMessage(s);
            return rr;
        }
        rr.setState(0);
        String[] s={"密码错误,登录失败"};
        rr.setMessage(s);
        return rr;
    }


    //注册
    @PostMapping("/reg.do")
    @ResponseBody
    public ResponseResult<Void> reg(String userName,String password){
        ResponseResult<Void> rr=new ResponseResult<>();
        User user=new User();
        user.setUserName(userName);
        user.setPassword(password);
        try{
            //判断用户名是否已经存在
            User u=iUserService.selectByName(userName);
            if(null==u){
                iUserService.insert(user);
            }else{
                String[] m=new String[]{"用户名已经存在,注册失败"};
                rr.setState(0);
                rr.setMessage(m);
                return rr;
            }
        }catch(Exception e){
            System.out.println("用户注册失败");
            String[] m=new String[]{"发生错误,注册失败"};
            rr.setState(0);
            rr.setMessage(m);
            return rr;
        }
        String[] m=new String[]{"注册成功"};
        rr.setState(1);
        rr.setMessage(m);
        return rr;
    }

}
