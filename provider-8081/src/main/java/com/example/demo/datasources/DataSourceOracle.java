package com.example.demo.datasources;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

/**
 * Oracle数据源
 * @author 类中域:徐林飞
 */
@Configuration
@MapperScan(basePackages = "com.example.demo.mapperOracle",sqlSessionFactoryRef = "primarySqlSessionFactory")
public class DataSourceOracle {

    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource primaryDataSource;

    @Bean(name = "primarySqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(primaryDataSource);
        return sessionFactoryBean.getObject();
    }

    @Bean(name = "primaryJdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate() {
        return new JdbcTemplate(primaryDataSource);
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateMySQL() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory());
        return template;
    }
    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
