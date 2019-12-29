package cn.xlf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author 类中域:徐林飞
 * @date 2019/12/26 15:55
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
//服务启动之后进行自动注册到Eureka
@EnableEurekaClient
//允许服务发现
@EnableDiscoveryClient
public class MyReflectApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyReflectApplication.class, args);
    }
}
