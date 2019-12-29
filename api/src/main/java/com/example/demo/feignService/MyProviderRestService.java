package com.example.demo.feignService;

import com.example.demo.bean.Node;
import com.example.demo.bean.User;
import com.example.demo.fallBackFactory.TestRestServiceFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 类中域:徐林飞
 */
@FeignClient(value="MYPROVIDER",fallbackFactory = TestRestServiceFallBackFactory.class)
public interface MyProviderRestService {
    @RequestMapping("/file/discovery")
    Object getService();

    @RequestMapping(value = "/updateUser")
    void update(@RequestBody User user);

    @RequestMapping(value = "/addNode")
    void addNode(@RequestBody Node node);

    @RequestMapping(value = "/delNode")
    void delNode(@RequestBody Node node);
    @RequestMapping(value = "/delNodeSon")
    void delNodeSon(@RequestBody Node node);

    @RequestMapping(value = "/updateNode")
    void updateNode(@RequestBody Node node);
    @RequestMapping(value = "/updateNodeDes")
    void updateNodeDes(@RequestBody Node node);

    @RequestMapping(value = "/selectListNodes")
    List<Node> selectListNodes(@RequestBody User user);
    @RequestMapping(value = "/selectOneNode")
    Node selectOneNode(@RequestBody Node node);
}
