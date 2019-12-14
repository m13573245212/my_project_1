package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * @author 类中域:徐林飞
 * @date 2019/11/23 00:09
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //路径与角色设置映射关系
        http.authorizeRequests()
//                .antMatchers("/yyy").permitAll()
                .antMatchers("/yyy").hasRole("root");
        //没有权限就跳转到登录页面
        http.formLogin();

        /**
         * 在使用SpringSecurity时,页面中使用iframe标签会报"in a frame because it set 'X-Frame-Options' to 'deny'."的错误,
         * 使用下面的设置可以规避该问题
         */
        http
                .headers()
                .frameOptions().sameOrigin()
                .httpStrictTransportSecurity().disable();

    }

    //认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                //在内存中设置
                .inMemoryAuthentication()
                //设置密码编码规则  实现自PasswordEncoder接口
                .passwordEncoder(new BCryptPasswordEncoder())
                //如果需要密码加密
                .withUser("徐林飞").password(new BCryptPasswordEncoder().encode("123")).roles("root")
                .and()
                .withUser("xu").password("123").roles("visitor");
    }
}
