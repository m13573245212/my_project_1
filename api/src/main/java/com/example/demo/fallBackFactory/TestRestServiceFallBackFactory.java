package com.example.demo.fallBackFactory;

import com.example.demo.bean.Node;
import com.example.demo.bean.Tes;
import com.example.demo.bean.User;
import com.example.demo.feignService.MyProviderRestService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 对同一个服务类中的 fallback 方法进行封装.
 * @author 类中域:徐林飞
 */
@Component
public class TestRestServiceFallBackFactory implements FallbackFactory<MyProviderRestService> {
    @Override
    public MyProviderRestService create(Throwable throwable) {
        return new MyProviderRestService() {
            @Override
            public Object getService() {
                return new Tes().setA("4444").setB("5555");
            }

            @Override
            public void update(User user) {

            }

            @Override
            public void addNode(Node node) {

            }

            @Override
            public void delNode(Node node) {

            }

            @Override
            public void delNodeSon(Node node) {

            }

            @Override
            public void updateNode(Node node) {

            }

            @Override
            public void updateNodeDes(Node node) {

            }

            @Override
            public List<Node> selectListNodes(User user) {
                return null;
            }

            @Override
            public Node selectOneNode(Node node) {
                return null;
            }
        };
    }
}
