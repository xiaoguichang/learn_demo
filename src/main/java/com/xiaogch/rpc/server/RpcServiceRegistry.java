package com.xiaogch.rpc.server;

import com.xiaogch.rpc.meta.RpcServiceMeta;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/16 16:48 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class RpcServiceRegistry {

    private static final Logger LOGGER = LogManager.getLogger(RpcServiceRegistry.class);

    private static final Map<String , RpcServiceMeta> rpcServiceMetaMap = new ConcurrentHashMap<>();

    private static final ReentrantLock reentrantLock = new ReentrantLock();

    public static RpcServiceMeta getRpcServiceMeta(String className) {
        Assert.notNull("clazz" , "clazz can't be null");
        if (rpcServiceMetaMap.containsKey(className)) {
            return rpcServiceMetaMap.get(className);
        }
        throw new IllegalArgumentException("can't find RpcServiceMeta for " + className);
    }

    public static boolean registerRpcServiceMeta(RpcServiceMeta rpcServiceMeta) {
        Assert.notNull("rpcServiceMeta" , "rpcServiceMeta can't be null");
        Assert.notNull(rpcServiceMeta.getClazz() , "attribute clazz of rpcServiceMeta can't be null");
        reentrantLock.lock();
        try {
            String className = rpcServiceMeta.getClazz().getName();
            if (rpcServiceMetaMap.containsKey(className)) {
                LOGGER.info("@@@@@ rpcServiceMeta for {} has existed" , className);
                return false;
            } else {
                rpcServiceMetaMap.put(className , rpcServiceMeta);
            }
            return true;
        } finally {
            reentrantLock.unlock();
        }
    }
}
