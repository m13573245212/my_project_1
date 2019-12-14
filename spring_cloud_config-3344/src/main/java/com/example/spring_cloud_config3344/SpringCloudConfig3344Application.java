package com.example.spring_cloud_config3344;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class SpringCloudConfig3344Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfig3344Application.class, args);
    }

}
