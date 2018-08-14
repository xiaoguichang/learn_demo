package com.xiaogch.common.redis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/14 17:37 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
@Component
public class RedisDatasourceFactory {

    private final static Logger LOGGER = LogManager.getLogger(RedisDatasourceFactory.class);

    /** key - beanId , value JedisPool bean*/
    @Autowired
    private Map<String , JedisPool> redisDataMap;

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setRedisDataSourceName(String redisDataSourceName){
        threadLocal.set(redisDataSourceName);
    }

    public static void unsetRedisDataSourceName() {
        threadLocal.remove();
    }

    public static String getRedisDataSourceName(){
        return threadLocal.get();
    }

    public JedisPool getJedisPool() {
        String redisDataSourceName = getRedisDataSourceName();
        LOGGER.debug("@@@@@ redisDataSourceName in threadLocal value is {} @@@@@" , redisDataSourceName);
        Assert.hasText(redisDataSourceName , "key can't be null");
        if (redisDataMap == null) {
            LOGGER.warn("@@@@@ redisDataMap is null @@@@@");
            throw new IllegalStateException("redisDataMap is null");
        }

        JedisPool jedisPool = redisDataMap.get(redisDataSourceName);
        if (jedisPool == null) {
            LOGGER.warn("@@@@@ redisDataSourceName=" + redisDataSourceName + " in redisDataMap is null @@@@@");
            throw new IllegalStateException("redisDataSourceName=" + redisDataSourceName + " in redisDataMap is null");
        }
        return jedisPool;
    }

}
