package cn.xlf.workflow.config;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;

/**
 * Activiti配置
 * @author 类中域:徐林飞
 * @date 2019/12/31 14:59
 */
@Configuration
@EnableConfigurationProperties
public class ActivitiConfig {
    @Autowired
    private DataSource dataSource;
    @Bean
    public ProcessEngineConfiguration processEngineConfiguration(PlatformTransactionManager transactionManager){
        SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
        processEngineConfiguration.setDataSource(dataSource);
        processEngineConfiguration.setDatabaseSchemaUpdate("true");
        processEngineConfiguration.setDatabaseType("oracle");

        processEngineConfiguration.setTransactionManager(transactionManager);

        return processEngineConfiguration;
    }
}
