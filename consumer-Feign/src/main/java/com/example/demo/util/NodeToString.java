package com.example.demo.util;

import com.example.demo.bean.Node;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 把查到 Node 转化为可以被 tree 识别的 String
 * @author 类中域:徐林飞
 */
public class NodeToString {
    
    public static String getData(List<Node> nodes){
        String result="";
        for (Node node : nodes) {
            if(null==node.getFartherId()){
                //先找到根节点
                if(!StringUtils.isEmpty(node.getDes())){
                    result="[{text:\""+ node.getNodeText()+" ( "+node.getDes()+" ) "+
                            "\",id:\""+ node.getNodeId()+ "\"";
                    result=getData2(nodes,node,result);
                    result+="}]";
                }else{
                    result="[{text:\""+ node.getNodeText()+
                            "\",id:\""+ node.getNodeId()+ "\"";
                    result=getData2(nodes,node,result);
                    result+="}]";
                }

            }
        }
        return result;
    }

    /**
     * 得到tree需要的字符串
     * @param nodes 所有节点
     * @param parentNode 父节点
     * @param result 最终需要返回的结果字符串
     * @return
     */
    public static String getData2(List<Node> nodes,Node parentNode,String result){
        result+=",nodes:[";
        for (Node node : nodes) {
            if(null!=node.getFartherId()){
                if(parentNode.getNodeId().equals(node.getFartherId().toString())){
                    if(!StringUtils.isEmpty(node.getDes())){
                        result+="{text:\""+node.getNodeText()+" ( "+node.getDes()+" ) "+"\",id:\""+node.getNodeId()+"\"";
                        result=getData2(nodes,node,result);
                        result+="},";
                    }else{
                        result+="{text:\""+node.getNodeText()+"\",id:\""+node.getNodeId()+"\"";
                        result=getData2(nodes,node,result);
                        result+="},";
                    }

                }
            }
        }
        //截取字符串,防止由于逻辑未走导致的错误.
        if(result.substring(result.length()-8).equals(",nodes:[")){
            result=result.substring(0,result.length()-8);
        }
        if(result.substring(result.length()-1).equals(",")){
            result=result.substring(0,result.length()-1);
            result+="]";
        }
        return result;
    }

    //为了在展示页面进行加载
    public static String getDataForZhan(List<Node> nodes){
        String result="";
        for (Node node : nodes) {
            if(null==node.getFartherId()){
                //先找到根节点
                if(!StringUtils.isEmpty(node.getDes())){
                    result="[{text:\""+ node.getNodeText()+
                            "\",id:\""+ node.getNodeId()+ "\",href:\""+node.getDes()+"\"";
                    result=getData2ForZhan(nodes,node,result);
                    result+="}]";
                }else{
                    result="[{text:\""+ node.getNodeText()+
                            "\",id:\""+ node.getNodeId()+ "\"";
                    result=getData2ForZhan(nodes,node,result);
                    result+="}]";
                }

            }
        }
        return result;
    }

    public static String getData2ForZhan(List<Node> nodes,Node parentNode,String result){
        result+=",nodes:[";
        for (Node node : nodes) {
            if(null!=node.getFartherId()){
                if(parentNode.getNodeId().equals(node.getFartherId().toString())){
                    if(!StringUtils.isEmpty(node.getDes())){
                        result+="{text:\""+node.getNodeText()+"\",id:\""+node.getNodeId()+"\",href:\""+node.getDes()+"\"";
                        result=getData2ForZhan(nodes,node,result);
                        result+="},";
                    }else{
                        result+="{text:\""+node.getNodeText()+"\",id:\""+node.getNodeId()+"\"";
                        result=getData2ForZhan(nodes,node,result);
                        result+="},";
                    }

                }
            }
        }
        //截取字符串,防止由于逻辑未走导致的错误.
        if(result.substring(result.length()-8).equals(",nodes:[")){
            result=result.substring(0,result.length()-8);
        }
        if(result.substring(result.length()-1).equals(",")){
            result=result.substring(0,result.length()-1);
            result+="]";
        }
        return result;
    }
}
