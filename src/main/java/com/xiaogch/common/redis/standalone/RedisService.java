package com.xiaogch.common.redis.standalone;

import com.xiaogch.common.redis.RedisException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.function.Function;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/4/4 0:14 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class RedisService {

    public final String STATUS_CODE_OK = "OK";

    static Logger LOGGER = LogManager.getLogger(RedisService.class);

    private JedisPool jedisPool;

    public RedisHashService buildRedisHashService(String key){
        return new RedisHashService(jedisPool , key);
    }

    public RedisStringService buildRedisStringService(String key){
        return new RedisStringService(jedisPool , key);
    }



    public RedisService(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    protected  <T> T execute(Function<Jedis , T> function) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return function.apply(jedis);
        } catch (Exception e) {
            throw new RedisException("redis operate exception" , e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 将Jedis实例返还JedisPool
     * @param jedis
     */
    public void returnResource(Jedis jedis) {
        try {
            if (jedis != null) {
                jedis.close();
            }
        } catch (Exception e) {
            LOGGER.error("return jedis connection to connectPool exception" , e);
        }
    }

    /**
     * 删除key
     * @param keys 要删除的keys
     * @return 返回删除的个数
     * @throws RedisException
     */
    public long del(String...keys) throws RedisException {
        return execute(jedis -> jedis.del(keys));
    }
}
