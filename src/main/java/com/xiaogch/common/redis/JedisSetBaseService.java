/**
 * ProjectName: jobrecommend-service <BR>
 * File name: JedisStringBaseService.java <BR>
 * Author: guich  <BR>
 * Project: jobrecommend-service <BR>
 * Version: v 1.0 <BR>
 * Date: 2017/10/25 11:13 <BR>
 * Description: redis SortedSet 结构操作 <BR>
 * Function List: <BR>
 */
package com.xiaogch.common.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisSetBaseService extends JedisBaseService {
    private final String STATUS_CODE_OK = "OK";

    private JedisPool jedisPool;
    private String key ;


    public JedisSetBaseService(JedisPool jedisPool, String key) {
        super(jedisPool);
        this.jedisPool = jedisPool;
        this.key = key;
    }

    public Long sadd(String key, String... members) throws RedisException{
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sadd(key, members);
        } catch (Exception e) {
            throw new RedisException("sadd set key from redis exception" , e);
        } finally {
            returnResource(jedis);
        }
    }


    public boolean srem(String key, String... members) throws RedisException{
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long value = jedis.srem(key, members);
            jedis.close();
            if(value > 0){
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RedisException("srem set key from redis exception" , e);
        } finally {
            returnResource(jedis);
        }
    }

}
