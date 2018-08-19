package com.xiaogch.rpc;

import com.xiaogch.rpc.meta.HostAndPort;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class RpcServiceClient {

    private static ReentrantLock reentrantLock = new ReentrantLock();

    private static final Map<String , RpcInvocationHandler> rpcInvocationHandlerMap = new ConcurrentHashMap<>();

    private final static RpcServiceClient RPC_SERVICE_CLIENT_INSTANCE = new RpcServiceClient();
    private RpcServiceClient() {

    }

    public static RpcServiceClient getInstance() {
        return RPC_SERVICE_CLIENT_INSTANCE;
    }

    public static <T> T getService(HostAndPort hostAndPort , Class<T> clazz) {
        RpcInvocationHandler rpcInvocationHandler = rpcInvocationHandlerMap.get(hostAndPort.toString());
        if (rpcInvocationHandler != null) {
            return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                    new Class[]{clazz}, rpcInvocationHandler);
        } else {
            reentrantLock.lock();
            try {
                rpcInvocationHandler = rpcInvocationHandlerMap.get(hostAndPort.toString());
                if (rpcInvocationHandler == null) {
                    rpcInvocationHandler = new RpcInvocationHandler(hostAndPort);
                    rpcInvocationHandlerMap.put(hostAndPort.toString() , rpcInvocationHandler);
                }
                return (T) Proxy.newProxyInstance(clazz.getClassLoader() , new Class[]{clazz} , rpcInvocationHandler);
            } finally {
                reentrantLock.unlock();
            }
        }
    }
}
