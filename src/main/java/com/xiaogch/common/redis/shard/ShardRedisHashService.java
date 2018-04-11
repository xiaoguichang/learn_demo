package com.xiaogch.common.redis.shard;

import com.alibaba.fastjson.JSONObject;
import com.xiaogch.common.redis.RedisException;
import redis.clients.jedis.ShardedJedisPool;

public class ShardRedisHashService extends ShardService {

    private ShardedJedisPool shardedJedisPool;
    private String key ;

    public ShardRedisHashService(ShardedJedisPool shardedJedisPool) {
        this(shardedJedisPool , "hash.default");
    }

    public ShardRedisHashService(ShardedJedisPool shardedJedisPool, String key) {
        super(shardedJedisPool);
        this.shardedJedisPool = shardedJedisPool;
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
