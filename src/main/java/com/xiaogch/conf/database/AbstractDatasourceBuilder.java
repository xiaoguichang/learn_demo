package com.xiaogch.conf.database;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * ProjectName: springboot<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: springboot <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/1/30 14:05 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
@Deprecated
public class AbstractDatasourceBuilder {

    protected DataSource getDataSource(Environment env, String prefix){
        Properties prop = build(env,prefix);
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.configFromPropety(prop);
        return druidDataSource;
    }

    /**
     * 主要针对druid数据库链接池
     * @param env
     * @param prefix
     * @return
     */
    protected Properties build(Environment env, String prefix) {
        Properties prop = new Properties();
        prop.put("druid.url", env.getProperty(prefix + ".url"));
        prop.put("druid.username", env.getProperty(prefix + ".username"));
        prop.put("druid.password", env.getProperty(prefix + ".password"));
        prop.put("druid.driverClassName", env.getProperty(prefix + ".driverClassName", ""));
        prop.put("druid.initialSize", env.getProperty(prefix + ".initialSize", Integer.class));
        prop.put("druid.maxActive", env.getProperty(prefix + ".maxActive", Integer.class));
        prop.put("druid.minIdle", env.getProperty(prefix + ".minIdle", Integer.class));
        prop.put("druid.maxWait", env.getProperty(prefix + ".maxWait", Integer.class));
        prop.put("druid.poolPreparedStatements", env.getProperty(prefix + ".poolPreparedStatements", Boolean.class));
        prop.put("druid.maxPoolPreparedStatementPerConnectionSize",
                env.getProperty(prefix + ".maxPoolPreparedStatementPerConnectionSize", Integer.class));
        prop.put("druid.maxPoolPreparedStatementPerConnectionSize",
                env.getProperty(prefix + ".maxPoolPreparedStatementPerConnectionSize", Integer.class));
        prop.put("druid.validationQuery", env.getProperty(prefix + ".validationQuery"));
        prop.put("druid.validationQueryTimeout", env.getProperty(prefix + ".validationQueryTimeout", Integer.class));
        prop.put("druid.testOnBorrow", env.getProperty(prefix + ".testOnBorrow", Boolean.class));
        prop.put("druid.testOnReturn", env.getProperty(prefix + ".testOnReturn", Boolean.class));
        prop.put("druid.testWhileIdle", env.getProperty(prefix + ".testWhileIdle", Boolean.class));
        prop.put("druid.timeBetweenEvictionRunsMillis", env.getProperty(prefix + ".timeBetweenEvictionRunsMillis", Integer.class));
        prop.put("druid.minEvictableIdleTimeMillis", env.getProperty(prefix + ".minEvictableIdleTimeMillis", Integer.class));
        prop.put("druid.filters", env.getProperty(prefix + ".filters"));
        return prop;
    }
}
