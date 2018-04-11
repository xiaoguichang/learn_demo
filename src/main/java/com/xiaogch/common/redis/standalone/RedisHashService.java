package com.xiaogch.common.redis.standalone;

import com.alibaba.fastjson.JSONObject;
import com.xiaogch.common.redis.RedisException;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Map;

public class RedisHashService extends RedisService {

    private JedisPool jedisPool;
    private String key ;

    public RedisHashService(JedisPool jedisPool) {
        this(jedisPool , "hash.default");
    }

    public RedisHashService(JedisPool jedisPool, String key) {
        super(jedisPool);
        this.jedisPool = jedisPool;
        this.key = key;
    }

    public String hget(String field) throws RedisException {
        return this.hget(key , field);
    }

    public String hget(String key , String field) throws RedisException {
        return execute(jedis -> jedis.hget(key , field));
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
        return execute(jedis -> jedis.hset(key , field, value));
    }

    public boolean hsetValue(String key , String field, Object value , int second) throws RedisException {
        return execute(jedis -> {
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
        });
    }


    public String hmset(String key , Map<String , String> fieldAndValues) throws RedisException {
        return execute(jedis -> jedis.hmset(key , fieldAndValues));
    }

    public List<String> hmget(String key , String...fields) throws RedisException {
        return execute(jedis -> jedis.hmget(key , fields));
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
        return execute(jedis -> jedis.hincrBy(key , field , increament));
    }

    public boolean hexist(String field) throws RedisException{
        return hexist(key , field);
    }

    public boolean hexist(String key , String field) throws RedisException{
        return execute(jedis -> jedis.hexists(key , field));
    }

    public Long hdel(String key , String...field) throws RedisException{
        return execute(jedis -> jedis.hdel(key , field));
    }

}
