package com.example.demo.service;

import com.example.demo.bean.Node;
import com.example.demo.bean.User;

import java.util.List;

/**
 * @author 类中域:徐林飞
 */
public interface IShowService {
    void addNode(Node node);
    void delNode(Node node);
    void delNodeSon(Node node);
    void updateNode(Node node);
    void updateNodeDes(Node node);
    List<Node> selectListNodes(User user);
    Node selectOneNode(Node node);
}
