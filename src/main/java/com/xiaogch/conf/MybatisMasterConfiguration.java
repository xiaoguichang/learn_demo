package com.xiaogch.conf;

import com.xiaogch.conf.database.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * ProjectName: springboot<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: springboot <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/1/30 12:45 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
@Configuration
@MapperScan(basePackages = "com.xiaogch.dao")
public class MybatisMasterConfiguration {

    private static Logger logger = LogManager.getLogger(MybatisMasterConfiguration.class);

    @Autowired
    DynamicDataSource dynamicDataSource;

    @Bean("sqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dynamicDataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath*:**/*Dao.xml");
        if (logger.isDebugEnabled()) {
            logger.debug("resources size is {}" , resources == null ? "0" : resources.length);
            if (resources != null) {
                for (Resource resource : resources) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("resource info is {}" , resource.getURL());
                    }
                }
            }
        }
        factoryBean.setMapperLocations(resources);
        return factoryBean.getObject();
    }

    @Bean(name = "sqlSessionTemplate")
    @Autowired
    @Qualifier("sqlSessionFactory")
    public SqlSessionTemplate getSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
