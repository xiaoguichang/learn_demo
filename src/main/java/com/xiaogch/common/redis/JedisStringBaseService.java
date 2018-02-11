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

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;

public class JedisStringBaseService extends JedisBaseService {
    private final String STATUS_CODE_OK = "OK";

    private JedisPool jedisPool;
    private String key ;

    public JedisStringBaseService(JedisPool jedisPool) {
        this(jedisPool , "string.default");
    }

    public JedisStringBaseService(JedisPool jedisPool, String key) {
        super(jedisPool);
        this.jedisPool = jedisPool;
        this.key = key;
    }

    public String get() throws RedisException {
        return this.get(key);
    }

    public String get(String key) throws RedisException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            throw new RedisException("get string key from redis exception" , e);
        } finally {
            returnResource(jedis);
        }
    }

    public boolean set(String value) throws RedisException {
        return this.set(key , value);
    }

    public boolean set(String key , String value) throws RedisException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String setResult = jedis.set(key , value);
            return STATUS_CODE_OK.equalsIgnoreCase(setResult);
        } catch (Exception e) {
            throw new RedisException("get string key from redis exception" , e);
        } finally {
            returnResource(jedis);
        }
    }

    public boolean set(String value , int second) throws RedisException {
        return this.set(key , value , second);
    }

    public boolean set(String key , String value , int second) throws RedisException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Transaction transaction = jedis.multi();
            transaction.set(key , value);
            transaction.expire(key , second);
            List<Object> result = transaction.exec();
            if (result != null && result.size() == 2) {
                Object setResult = result.get(0);
                Object expireResulr = result.get(1);
                if (setResult != null && expireResulr != null &&
                        STATUS_CODE_OK.equalsIgnoreCase(setResult.toString())
                        && "1".equalsIgnoreCase(expireResulr.toString())){
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw new RedisException("get string key from redis exception" , e);
        } finally {
            returnResource(jedis);
        }
    }

    /***
     * 获取redis分布式锁
     * @param lockTime 锁持续时间（毫秒）
     * @param timeOut 超时时间（毫秒）
     * @return true：获得锁，false：未获得锁
     * @throws RedisException
     */
    public boolean getLock(int lockTime , long timeOut) throws RedisException {
        return getLock(key , lockTime , timeOut);
    }


    /***
     * 获取redis分布式锁
     * @param lockName 锁名称
     * @param lockTime 锁持续时间（毫秒）
     * @param timeOut 超时时间（毫秒）
     * @return true：获得锁，false：未获得锁
     * @throws RedisException
     */
    public boolean getLock(String lockName , int lockTime , long timeOut) throws RedisException {
        Jedis jedis = null;
        long beginTime = System.currentTimeMillis();
        try {
            while (System.currentTimeMillis() - beginTime < timeOut) {
                try {
                    jedis = jedisPool.getResource();
                    String setResult = jedis.set(lockName, "lock ok", "NX", "PX", lockTime);
                    if (STATUS_CODE_OK.equalsIgnoreCase(setResult)) {
                        return true;
                    }
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
        } catch (Exception e) {
            throw new RedisException("RedisLock getLock lockName=" + lockName + " Exception" , e);
        } finally {
            returnResource(jedis);
        }
        return false;
    }

    /***
     * 获取redis分布式锁
     * @param lockName 锁名称
     * @return true：获得锁，false：释放锁失败
     * @throws RedisException
     */
    public boolean releaseLock(String lockName) throws RedisException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(lockName);
            return true;
        } catch (Exception e) {
            throw new RedisException("RedisLock releaseLock lockName=" + lockName + " Exception" , e);
        } finally {
            returnResource(jedis);
        }
    }

    /***
     * 释放redis分布式锁
     * @return true：释放锁成功，false：释放锁失败
     * @throws RedisException
     */
    public boolean releaseLock() throws RedisException {
        return releaseLock(key);
    }

    public <T> T getValue(String key , Class<T> tClass) throws RedisException {
        String value = get(key);
        if (value != null) {
            logger.info("key={} , value={}" , key , value);
            return JSONObject.parseObject(value , tClass);
        }
        return null;
    }

    public boolean setValue(String key , Object value , int second) throws RedisException {
        String valueJson = JSONObject.toJSONString(value);
        return set(key , valueJson , second);
    }

    public List<String> mget(String...keys) throws RedisException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.mget(keys);
        } catch (Exception e) {
            throw new RedisException("redis mget Exception" , e);
        } finally {
            returnResource(jedis);
        }
    }

    public Long incr(String key) throws RedisException  {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.incr(key);
        } catch (Exception e) {
            throw new RedisException("redis incr Exception" , e);
        } finally {
            returnResource(jedis);
        }
    }
}
