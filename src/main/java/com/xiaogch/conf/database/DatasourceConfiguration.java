package com.xiaogch.conf.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * ProjectName: springboot<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: springboot <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/1/30 12:15 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
@Configuration
@EnableTransactionManagement
//public class DatasourceConfiguration extends AbstractDatasourceBuilder{
public class DatasourceConfiguration {
    private static Logger logger = LoggerFactory.getLogger(DatasourceConfiguration.class);

    @Value("${spring.datasource.type}")
    Class<? extends DataSource> type;

    @Bean(name = "slaveDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDatasource() {
        logger.info("create slaveDatasource type={}" , type);
        return DataSourceBuilder.create().type(type).build();
    }


    @Bean(name = "masterDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDatasource() {
        logger.info("create masterDatasource type={}" , type);
        return DataSourceBuilder.create().type(type).build();
    }

//    public DataSource dataSource(Environment environment) {
//        DataSource dataSource = getDataSource(environment , "spring.datasource.master");
//        return dataSource;
//    }


    @Bean(name = "dynamicDatasource")
    public DynamicDataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource(0);
        Map<Object , Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DatasourceType.MASTER.getType() + "_0" , masterDatasource());
        targetDataSource.put(DatasourceType.SLAVE.getType()+ "_0" , slaveDatasource());
        dynamicDataSource.setTargetDataSources(targetDataSource);
        dynamicDataSource.setDefaultTargetDataSource(masterDatasource());
        return dynamicDataSource;
    }

    @Bean(name = "masterDataSourceTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(masterDatasource());
        return dataSourceTransactionManager;
    }
}
