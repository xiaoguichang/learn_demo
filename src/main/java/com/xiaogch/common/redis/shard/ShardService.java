package com.xiaogch.common.redis.shard;

import com.xiaogch.common.redis.RedisException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.function.Function;


/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/4/4 1:25 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class ShardService {
    public final String STATUS_CODE_OK = "OK";
    static Logger LOGGER = LogManager.getLogger(ShardService.class);

    private ShardedJedisPool shardedJedisPool;

    public ShardService() {

    }

    public ShardService(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }

    public <T> T execute(Function<ShardedJedis , T> shardRedisFuture) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardRedisFuture.apply(shardedJedis);
        } catch (Exception e) {
            throw new RedisException("redis operate exception" , e);
        } finally {
            returnResource(shardedJedis);
        }
    }

    /**
     * 将ShardedJedis实例返还ShardedJedisPool
     * @param shardedJedis
     */
    public void returnResource(ShardedJedis shardedJedis) {
        try {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        } catch (Exception e) {
            LOGGER.error("return jedis connection to connectPool exception" , e);
        }
    }

    /**
     * 删除key
     * @param key 要删除的key
     * @return 返回删除的个数
     * @throws RedisException
     */
    public long del(String key) throws RedisException {
        return execute(shardedJedis -> shardedJedis.del(key));
    }


    public ShardedJedisPool getShardedJedisPool() {
        return shardedJedisPool;
    }

    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }
}
