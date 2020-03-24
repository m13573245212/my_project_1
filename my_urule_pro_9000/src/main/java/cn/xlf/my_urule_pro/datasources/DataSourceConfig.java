package cn.xlf.my_urule_pro.datasources;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * uRule需要数据库
 * @author 类中域:徐林飞
 */
@Configuration
public class DataSourceConfig {


    @Bean
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource datasource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(datasource());
        return sessionFactoryBean.getObject();
    }

    @Bean
    public JdbcTemplate primaryJdbcTemplate() {
        return new JdbcTemplate(datasource());
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateOracle1() throws Exception {
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
