package com.example.demo.controller;

import com.example.demo.bean.Tes;
import com.example.demo.service.IFileService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 类中域:徐林飞
 */
@RestController
public class FileController {
    @Autowired
    private IFileService iFileService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/test/rest")
    public List selectAllFile(){
        return iFileService.selectAll();
    }
    //用于服务发现
    @RequestMapping("/file/discovery")
    @HystrixCommand(fallbackMethod = "hystrix")
    public Object getServices(){
        List<String> list=discoveryClient.getServices();
        System.out.println("-------:"+list+"--------");
        //获取指定服务的实例的信息，最好大写，与Eureka显示的一致
        List<ServiceInstance> serviceList=discoveryClient.getInstances("MYPROVIDER");
        for (ServiceInstance element : serviceList) {
//            System.out.println("--------------服务信息：开始-------------");
//            System.out.println(element.getHost()+"\t"+element.getPort()+"\t"+
//            element.getServiceId()+"\t"+element.getMetadata()+"\t"+element.getInstanceId());
//            System.out.println("--------------服务信息：结束-------------");
        }
        System.out.println("----------------------"+8081);

        //实验服务熔断,使用一个不存在的服务,如果查不到就抛异常.
        List<ServiceInstance> serviceList2=discoveryClient.getInstances("MYPROVIDER");
        if(serviceList2.size()==0){
            throw new RuntimeException();
        }
        return discoveryClient;
    }

    public Object hystrix(){
        return new Tes().setA("1111").setB("2222");
    }
}