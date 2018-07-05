package com.xiaogch.common.redis.shard;

import com.alibaba.fastjson.JSONObject;
import com.xiaogch.common.redis.RedisException;
import com.xiaogch.common.redis.standalone.RedisService;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/4/4 0:20 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class ShardStringService extends ShardService {

    private ShardedJedisPool shardedJedisPool;
    private String key ;

    public ShardStringService(ShardedJedisPool shardedJedisPool) {
        this(shardedJedisPool , "string.default");
    }

    public ShardStringService(ShardedJedisPool shardedJedisPool, String key) {
        super(shardedJedisPool);
        this.shardedJedisPool = shardedJedisPool;
        this.key = key;
    }

    public String get() {
        return this.get(key);
    }

    public String get(String key) {
        return execute(jedis -> jedis.get(key));
    }

    public boolean set(String value) {
        return this.set(key , value);
    }

    public boolean set(String key , String value) {
        return execute(jedis -> {
            String setResult = jedis.set(key , value);
            return STATUS_CODE_OK.equalsIgnoreCase(setResult);
        });
    }

    public boolean set(String value , int second) {
        return this.set(key , value , second);
    }

    public boolean set(String key , String value , int second) {
        return execute(jedis -> {
            jedis.set(key , value);
            jedis.expire(key , second);
            return false;
        });
    }

    /***
     * 获取redis分布式锁
     * @param lockTime 锁持续时间（毫秒）
     * @param timeOut 超时时间（毫秒）
     * @return true：获得锁，false：未获得锁
     * @throws RedisException
     */
    public boolean getLock(int lockTime , long timeOut) {
        return getLock(key , lockTime , timeOut);
    }


    /***
     * 获取redis分布式锁
     * @param lockName 锁名称
     * @param lockTime 锁持续时间（毫秒）
     * @param timeOut 超时时间（毫秒）
     * @return true：获得锁，false：未获得锁d
     * @throws RedisException
     */
    public boolean getLock(String lockName , int lockTime , long timeOut) {
        return execute(jedis -> {
            long beginTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - beginTime < timeOut) {
                try {
                    String setResult = jedis.set(lockName, "lock ok", "NX", "PX", lockTime);
                    if (STATUS_CODE_OK.equalsIgnoreCase(setResult)) {
                        return true;
                    }
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            return false;
        });
    }

    /***
     * 获取redis分布式锁
     * @param lockName 锁名称
     * @return true：获得锁，false：释放锁失败
     * @throws RedisException
     */
    public boolean releaseLock(String lockName) {
        return execute(jedis -> {
            jedis.del(lockName);
            return true;
        });
    }

    /***
     * 释放redis分布式锁
     * @return true：释放锁成功，false：释放锁失败
     * @throws RedisException
     */
    public boolean releaseLock() {
        return releaseLock(key);
    }

    public <T> T getValue(String key , Class<T> tClass) {
        String value = get(key);
        if (value != null) {
            LOGGER.info("key={} , value={}" , key , value);
            return JSONObject.parseObject(value , tClass);
        }
        return null;
    }

    public boolean setValue(String key , Object value , int second) {
        String valueJson = JSONObject.toJSONString(value);
        return set(key , valueJson , second);
    }

    public Long incr(String key)  {
        return execute(jedis -> jedis.incr(key));

    }
}
