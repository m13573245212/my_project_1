package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * provider作为服务的提供方
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
//服务启动之后进行自动注册到Eureka
@EnableEurekaClient
//允许服务发现
@EnableDiscoveryClient
//允许服务熔断
@EnableCircuitBreaker
//开启定时任务
//@EnableScheduling
//redis
//@EnableRedisRepositories
public class ProviderApplication8082 {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication8082.class, args);
        try{
            System.in.read();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

