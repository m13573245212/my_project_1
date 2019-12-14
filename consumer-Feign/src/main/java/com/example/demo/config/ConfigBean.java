package com.example.demo.config;

import com.example.lbrule.MyRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author 类中域:徐林飞
 */
@Configuration
public class ConfigBean {
    //Rest调用需要的Bean
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

//    @Bean
//    public IRule myRule(){
////        return new RetryRule();
////        return new RoundRobinRule();
////        return new RandomRule();
//        return new MyRule();
//    }
}
