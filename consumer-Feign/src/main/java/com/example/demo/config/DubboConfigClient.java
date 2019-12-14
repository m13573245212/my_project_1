package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * @author 将来的大拿:徐林飞
 */
@Configuration
@PropertySource("classpath:dubbo/dubbo.properties")
@ImportResource("classpath:dubbo/*.xml")
public class DubboConfigClient {

}
