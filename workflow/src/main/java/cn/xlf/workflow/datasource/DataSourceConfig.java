package cn.xlf.workflow.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author 类中域:徐林飞
 */
@Configuration
public class DataSourceConfig {
//
//    @Bean("dataSource")
//    @ConfigurationProperties(prefix="spring.datasource")
//    public DataSource dataSource(){
//        return DataSourceBuilder.create().build();
//    }

}
