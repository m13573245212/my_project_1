package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * dubbo配置类
 * @author 类中域:徐林飞
 */
@Configuration
@PropertySource("classpath:dubbo/dubbo.properties")
@ImportResource("classpath:dubbo/*.xml")
public class DubboConfig {

}
