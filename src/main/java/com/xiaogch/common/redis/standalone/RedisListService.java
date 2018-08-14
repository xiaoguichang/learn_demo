package com.xiaogch.common.redis.standalone;

import com.alibaba.fastjson.JSONObject;
import com.xiaogch.common.redis.RedisException;
import org.springframework.util.StringUtils;
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
public class RedisListService extends RedisService {

    /**
     * 返回列表长度
     *
     * @param key
     * @return
     * @throws RedisException
     */
    public Long llen(String key) throws RedisException {
        return execute(jedis ->  jedis.llen(key));
    }

    public String lpop(String key) throws RedisException {
        return execute(jedis -> jedis.lpop(key));
    }

    /**
     * 在列表中的尾部添加一个个值，返回列表的长度
     *
     * @param key
     * @param value
     * @return Long
     */
    public Long rpush(String key, String value) throws RedisException {
        return execute(jedis -> jedis.rpush(key , value));

    }

    public boolean rpush(String key, Object value, int second) throws RedisException {
        return execute(jedis -> {
            Transaction transaction = jedis.multi();
            String valueJson = JSONObject.toJSONString(value);
            transaction.rpush(key, valueJson);
            transaction.expire(key, second);
            List<Object> result = transaction.exec();
            if (result != null && result.size() == 2) {
                return true;
            }
            return false;
        });
    }

    public <T> T lpop(String key, Class<T> clazz) throws RedisException {
        return execute(jedis -> {
            String value = jedis.lpop(key);
            if (!StringUtils.hasText(value)) {
                return JSONObject.parseObject(value, clazz);
            }
            return null;
        });
    }
}
