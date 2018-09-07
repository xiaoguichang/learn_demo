package com.xiaogch.rpc.util;

import com.xiaogch.entity.SysParameterEntity;
import com.xiaogch.entity.UserInfoEntity;
import com.xiaogch.rpc.RpcRequest;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;

import java.util.ArrayList;
import java.util.List;
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


    public static void main(String[] args) {
        System.setProperty("protostuff.runtime.allow_null_array_element" , "true");
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setParameterTypesAndValues(new Class[]{RpcRequest.class} , new Object[]{new SysParameterEntity() , null , new UserInfoEntity()});
        byte[] bytes = serializeByProtostuff(rpcRequest);

        RpcRequest newRpcRequest = deserializeByProtostuff(bytes , RpcRequest.class);

        Test test = new Test();
        test.getList().add(new SysParameterEntity());
        test.getList().add(null);
        test.getList().add(new SysParameterEntity());
        test.getList().add(null);
        test.getList().add(new SysParameterEntity());
        test.getList().add(null);
        test.getList().add(new SysParameterEntity());

        byte[] bytesTest = serializeByProtostuff(test);

//        Test newTest = deserializeByProtostuff(bytesTest , Test.class);

        System.out.println(newRpcRequest);
    }
}

class Test {

    List<SysParameterEntity> list = new ArrayList<>();

    public List<SysParameterEntity> getList() {
        return list;
    }

    public void setList(List<SysParameterEntity> list) {
        this.list = list;
    }
}
