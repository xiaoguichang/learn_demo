package com.xiaogch.rpc.util;

import com.alibaba.fastjson.JSONObject;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/16 15:25 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class SerializedUtil {

    private static final Map<Class<?>, Schema<?>> CACHED_SCHEMA = new ConcurrentHashMap<>();

    private static final Objenesis OBJENESIS = new ObjenesisStd(true);

    /**
     * 获取
     * @param clazz
     * @param <T>
     * @return
     */
    private static <T> Schema<T> getSchema(Class<T> clazz) {
        @SuppressWarnings("unchecked") Schema<T> schema = (Schema<T>) CACHED_SCHEMA.get(clazz);
        if (schema == null) {
            schema = RuntimeSchema.getSchema(clazz);
            if (schema != null) {
                CACHED_SCHEMA.put(clazz, schema);
            }
        }
        return schema;
    }

    /** 序列化 * * @param obj * @return */
    public static <T> byte[] serializeByProtostuff(T obj) {
        Class<T> clazz = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = getSchema(clazz);
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    /** 反序列化 * * @param data * @param clazz * @return */
    public static <T> T deserializeByProtostuff(byte[] data, Class<T> clazz) {
        try {
            T obj = OBJENESIS.newInstance(clazz);
            Schema<T> schema = getSchema(clazz);
            ProtostuffIOUtil.mergeFrom(data, obj, schema);
            return obj;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
