package com.example.demo;

import com.example.lbrule.MyRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Consume作为服务的消费方
 */
@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "MYPROVIDER", configuration = MyRule.class)
@EnableFeignClients
public class ConsumeApplicationFeign {

    public static void main(String[] args) {
        SpringApplication.run(ConsumeApplicationFeign.class, args);
    }

}

