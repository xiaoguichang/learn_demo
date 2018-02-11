package com.xiaogch.common.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisListBaseService extends JedisBaseService {
    private final String STATUS_CODE_OK = "OK";

    private JedisPool jedisPool;
    private String key ;

    public JedisListBaseService(JedisPool jedisPool) {
        this(jedisPool , "hash.default");
    }

    public JedisListBaseService(JedisPool jedisPool, String key) {
        super(jedisPool);
        this.jedisPool = jedisPool;
        this.key = key;
    }


    public String lpop(String key) throws RedisException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lpop(key);
        } catch (Exception e) {
            throw new RedisException("get string key from redis exception" , e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 在列表中的尾部添加一个个值，返回列表的长度
     * @param key
     * @param value
     * @return Long
     */
    public Long rpush(String key, String value) throws RedisException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.rpush(key, value);
        } catch (Exception e) {
            throw new RedisException("get string key from redis exception" , e);
        } finally {
            returnResource(jedis);
        }
    }

}
