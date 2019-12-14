package com.example.demo.controller;

import com.example.demo.bean.Node;
import com.example.demo.bean.User;
import com.example.demo.feignService.MyProviderRestService;
import com.example.demo.util.NodeToString;
import com.example.demo.vo.ResponseResult;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 设置页面Controller
 * @author 类中域:徐林飞
 */
@RestController
@Log4j
@RequestMapping("/show")
public class ShowController {
    @Autowired
    private MyProviderRestService myProviderRestService;

    @RequestMapping("")
    public ModelAndView getPage(ModelAndView modelAndView){
        modelAndView.setViewName("main_home/showHtml");
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

    //返回可用的节点ID
    @RequestMapping("/getNewNodeId")
    @ResponseBody
    public Integer getNodeId(HttpSession session){
        //查询这个人的节点
        User user=(User)session.getAttribute("user");
        List<Node> nodes=myProviderRestService.selectListNodes(user);

        //已经被使用的id
        List<Integer> ids=new ArrayList<>();
        if (null!=nodes){
            for(Node node:nodes){
                ids.add(Integer.parseInt(node.getNodeId()));
            }
        }else{
            return -1;
        }

        //生成一个节点id,不能与ids中的重复
        int result=0;
        while(true){
            int s=(int)(Math.random()*3000);
            if(!ids.contains(s)){
                result=s;
                break;
            }
        }
        return result;
    }

    //请求tree
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
                data=NodeToString.getData(nodes);
            }else{
                data="[{text:\"根节点\"}]";
                //为该用户新建一个根节点
                Node node=new Node();
                node.setNodeId("0")
                        .setMasterId(user.getId())
                        .setNodeText("根节点");
                myProviderRestService.addNode(node);
            }
        }

        return data;
    }

    //新增节点
    @RequestMapping("/addPoint")
    @ResponseBody
    public ResponseResult<Void> addPoint(Node node,HttpSession session){
        ResponseResult<Void> rr=new ResponseResult<>();
        try {
            User user=(User)session.getAttribute("user");
            node.setMasterId(user.getId());
            myProviderRestService.addNode(node);
            rr.setState(0);
        }catch(Exception e){
            e.printStackTrace();
            rr.setState(1);
            rr.setMessage(new String[]{e.getMessage()});
        }
        return rr;
    }
    //删除节点
    @RequestMapping("/deletePoint")
    @ResponseBody
    public ResponseResult<Void> delPoint(Node node,HttpSession session){
        ResponseResult<Void> rr=new ResponseResult<>();

        User user=(User)session.getAttribute("user");
        if(null==user){
            rr.setState(1);
            rr.setMessage(new String[]{"登录超时,需要重新登录"});
        }else{
            node.setMasterId(user.getId());
            myProviderRestService.delNode(node);
            //删除父节点是这个的所有节点
            myProviderRestService.delNodeSon(node);
            rr.setState(0);
            rr.setMessage(new String[]{"删除成功"});
        }
        return rr;
    }

    @RequestMapping("/updatePoint")
    @ResponseBody
    public ResponseResult<Void> updatePoint(Node node, HttpSession session){
        ResponseResult<Void> rr=new ResponseResult<>();

        User user=(User)session.getAttribute("user");
        if(null==user){
            rr.setState(1);
            rr.setMessage(new String[]{"登录超时,需要重新登录"});
        }else{
            node.setMasterId(user.getId());
            //查一下是不是本来就绑定了链接
            Node n=myProviderRestService.selectOneNode(node);
            if(!StringUtils.isEmpty(n.getDes())){
                myProviderRestService.updateNode(node);
                rr.setState(2);
                rr.setMessage(new String[]{"修改成功",n.getDes()});
            }else{
                myProviderRestService.updateNode(node);
                rr.setState(0);
                rr.setMessage(new String[]{"修改成功"});
            }
        }
        return rr;
    }

    @RequestMapping("/conn")
    @ResponseBody
    public ResponseResult<Void> conn(Node node,HttpSession session){
        ResponseResult<Void> rr=new ResponseResult<>();

        User user=(User)session.getAttribute("user");
        if(null==user){
            rr.setState(1);
            rr.setMessage(new String[]{"登录超时,需要重新登录"});
        }else{
            node.setMasterId(user.getId());
            //查一下是不是本来就绑定了链接
            Node n=myProviderRestService.selectOneNode(node);
            if(!StringUtils.isEmpty(n.getDes())){
                myProviderRestService.updateNodeDes(node);
                rr.setState(2);
                rr.setMessage(new String[]{"链接成功"});
            }else{
                myProviderRestService.updateNodeDes(node);
                rr.setState(0);
                rr.setMessage(new String[]{"链接成功"});
            }
        }
        return rr;
    }
}
