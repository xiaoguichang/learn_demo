package com.xiaogch.conf;

import com.xiaogch.common.redis.RedisDatasourceFactory;
import com.xiaogch.common.redis.RedisDatasourceSelectAop;
import com.xiaogch.common.redis.standalone.RedisHashService;
import com.xiaogch.common.redis.standalone.RedisListService;
import com.xiaogch.common.redis.standalone.RedisStringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by Administrator on 2018/2/5 0005.
 */

@Configuration
@ConfigurationProperties(prefix = "redis")
public class RedisConfiguration {

    @Bean
    public JedisPoolConfig buildJedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(2048);
        jedisPoolConfig.setMaxIdle(128);
        return jedisPoolConfig;
    }

    @Bean("defaultRedisDatasource")
    @Autowired
    public JedisPool buildDefaultJedisPool(JedisPoolConfig jedisPoolConfig) {
        JedisPool jedisPool = new JedisPool(jedisPoolConfig , "127.0.0.1",6379);
        return jedisPool;
    }

    @Bean("slaveRedisDatasource")
    @Autowired
    public JedisPool buildSlaveJedisPool(JedisPoolConfig jedisPoolConfig) {
        JedisPool jedisPool = new JedisPool(jedisPoolConfig , "127.0.0.1",6379);
        return jedisPool;
    }

    @Bean
    public RedisDatasourceFactory buildRedisDatasourceFactory(){
        return new RedisDatasourceFactory();
    }

    @Bean
    public RedisDatasourceSelectAop buildRedisDatasourceSelectAop(){
        return new RedisDatasourceSelectAop();
    }

    @Bean
    public RedisListService buildRedisListService(){
        return new RedisListService();
    }

    @Bean
    public RedisStringService buildRedisStringService(){
        return new RedisStringService();
    }

    @Bean
    public RedisHashService buildRedisHashService(){
        return new RedisHashService();
    }

}
