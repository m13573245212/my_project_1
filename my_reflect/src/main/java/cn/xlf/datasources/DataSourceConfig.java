package cn.xlf.datasources;

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

    @Bean("primaryDataSource")
    @ConfigurationProperties(prefix="spring.datasource.primary")
    public DataSource dbOracle(){
        return DataSourceBuilder.create().build();
    }

    @Bean("secondaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource dbMySQL(){
        return DataSourceBuilder.create().build();
    }
}
