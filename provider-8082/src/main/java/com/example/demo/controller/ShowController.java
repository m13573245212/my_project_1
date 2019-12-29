package com.example.demo.controller;

import com.example.demo.bean.Node;
import com.example.demo.bean.User;
import com.example.demo.service.IShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author 类中域:徐林飞
 */
@RestController
public class ShowController {
    @Autowired
    private IShowService iShowService;

    @RequestMapping("/addNode")
    public void addNode(@RequestBody Node node){
        iShowService.addNode(node);
    }

    @RequestMapping("/delNode")
    public void delNode(@RequestBody Node node){
        iShowService.delNode(node);
    }

    @RequestMapping("/delNodeSon")
    public void delNodeSon(@RequestBody Node node){
        iShowService.delNodeSon(node);
    }

    @RequestMapping("/updateNode")
    public void updateNode(@RequestBody Node node){
        iShowService.updateNode(node);
    }

    @RequestMapping("/selectListNodes")
    public List<Node> selectListNodes(@RequestBody User user){
        return iShowService.selectListNodes(user);
    }
    @RequestMapping("/selectOneNode")
    public Node selectOneNode(@RequestBody Node node){
        return iShowService.selectOneNode(node);
    }

    @RequestMapping("/updateNodeDes")
    public void updateNodeDes(@RequestBody Node node){
        iShowService.updateNodeDes(node);
    }
}
