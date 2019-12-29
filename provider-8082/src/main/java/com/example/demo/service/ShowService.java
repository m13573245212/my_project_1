package com.example.demo.service;

import com.example.demo.bean.Node;
import com.example.demo.bean.User;
import com.example.demo.mapperMySQL.IShowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author 类中域:徐林飞
 */
@Service
public class ShowService implements IShowService{

    @Autowired
    private IShowMapper iShowMapper;

    @Override
    public void addNode(Node node) {
        iShowMapper.addNode(node);
    }

    @Override
    public void delNode(Node node) {
        iShowMapper.delNode(node);
    }

    @Override
    public void delNodeSon(Node node) {
        iShowMapper.delNodeSon(node);
    }

    @Override
    public void updateNode(Node node) {
        iShowMapper.updateNode(node);
    }

    @Override
    public void updateNodeDes(Node node) {
        iShowMapper.updateNodeDes(node);
    }

    @Override
    public List<Node> selectListNodes(User user) {
        return iShowMapper.selectListNodes(user);
    }

    @Override
    public Node selectOneNode(Node node) {
        return iShowMapper.selectOneNode(node);
    }
}
