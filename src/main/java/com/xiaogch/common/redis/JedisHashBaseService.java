package com.xiaogch.common.redis;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Map;

public class JedisHashBaseService extends JedisBaseService {
    private final String STATUS_CODE_OK = "OK";

    private JedisPool jedisPool;
    private String key ;

    public JedisHashBaseService(JedisPool jedisPool) {
        this(jedisPool , "hash.default");
    }

    public JedisHashBaseService(JedisPool jedisPool, String key) {
        super(jedisPool);
        this.jedisPool = jedisPool;
        this.key = key;
    }


    public String hget(String field) throws RedisException {
        return this.hget(key , field);
    }

    public String hget(String key , String field) throws RedisException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hget(key , field);
        } catch (Exception e) {
            throw new RedisException("get string key from redis exception" , e);
        } finally {
            returnResource(jedis);
        }
    }

    public <T> T hget(String key , String field, Class<T> tClass) throws RedisException {
        String value = hget(key , field);
        logger.info("key={} filed={} value in redis is {}" , key , field , value);
        if (value != null) {
            return JSONObject.parseObject(value , tClass);
        }
        return null;
    }

    public Long hset(String field , String value) throws RedisException {
        return this.hset(key , field , value);
    }

    /***
     *
     * @param key
     * @param field
     * @param value
     * @return 1-设置成功，且field之前不存在，0-设置成功，且field之前存在
     * @throws RedisException
     */
    public Long hset(String key , String field , String value) throws RedisException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hset(key , field, value);
        } catch (Exception e) {
            throw new RedisException("get hash key from redis exception" , e);
        } finally {
            returnResource(jedis);
        }
    }


    public boolean hsetValue(String key , String field, Object value , int second) throws RedisException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Transaction transaction = jedis.multi();
            String valueJson = JSONObject.toJSONString(value);

            transaction.hset(key , field , valueJson);
            transaction.expire(key , second);
            List<Object> result = transaction.exec();
            if (result != null && result.size() == 2) {
                Object expireResulr = result.get(1);
                if (expireResulr != null && "1".equalsIgnoreCase(expireResulr.toString())){
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw new RedisException("set value to redis exception" , e);
        } finally {
            returnResource(jedis);
        }
    }



    public String hmset(String key , Map<String , String> fieldAndValues) throws RedisException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hmset(key , fieldAndValues);
        } catch (Exception e) {
            throw new RedisException("redis hmset Exception" , e);
        } finally {
            returnResource(jedis);
        }
    }

    public List<String> hmget(String key , String...fields) throws RedisException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hmget(key , fields);
        } catch (Exception e) {
            throw new RedisException("redis hmget Exception" , e);
        } finally {
            returnResource(jedis);
        }
    }

    public Long hincr(String field) throws RedisException {
        return hincr(key , field , 1l);
    }

    public Long hincr(String field , long increament) throws RedisException {
        return hincr(key , field , increament);

    }
    public Long hincr(String key , String field) throws RedisException {
        return hincr(key , field , 1l);
    }

    public Long hincr(String key , String field , long increament) throws RedisException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hincrBy(key , field , increament);
        } catch (Exception e) {
            throw new RedisException("redis hincr Exception" , e);
        } finally {
            returnResource(jedis);
        }
    }

    public boolean hexist(String field) throws RedisException{
        return hexist(key , field);
    }

    public boolean hexist(String key , String field) throws RedisException{
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hexists(key , field);
        } catch (Exception e) {
            throw new RedisException("redis hexist Exception" , e);
        } finally {
            returnResource(jedis);
        }
    }


    public Long hdel(String key , String field) throws RedisException{
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hdel(key , field);
        } catch (Exception e) {
            throw new RedisException("redis hexist Exception" , e);
        } finally {
            returnResource(jedis);
        }
    }

}
