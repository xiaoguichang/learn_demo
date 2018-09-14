package com.xiaogch.rpc.server;

import com.xiaogch.rpc.annotation.RpcMethod;
import com.xiaogch.rpc.annotation.RpcService;
import com.xiaogch.rpc.meta.RpcMethodHandler;
import com.xiaogch.rpc.meta.RpcServiceMeta;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: guich <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/15 19:21 <BR>
 * Description: 服务启动时 自动扫描注册RPC服务，并启动RPC服务 <BR>
 * Function List:  <BR>
 */
public class RpcServiceScanApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

    static Logger LOGGER = LogManager.getLogger(RpcServiceScanApplicationListener.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ApplicationContext context = event.getApplicationContext();
        Map<String , Object> beans = context.getBeansWithAnnotation(RpcService.class);

        // 扫描 RPC 方法
        LOGGER.info("@@@@@ get beans for RpcService Annotation beans size is {}" , beans == null ? 0 : beans.size());
        if (beans != null) {
            beans.forEach((String key , Object object) -> {
                Class clazz = object.getClass();
                Class[] interfaces = clazz.getInterfaces();
                if (interfaces != null && interfaces.length > 0) {
                    for (Class tempClazz : interfaces) {
                        LOGGER.info("@@@@@ RpcService Annotation tempClazz={}" , tempClazz.getClass());
                        Annotation annotation = tempClazz.getAnnotation(RpcService.class);
                        if (annotation != null) {
                            RpcServiceRegistry.registerRpcServiceMeta(resolveRpcServiceMeta(tempClazz , object));
                        }
                    }
                }

                LOGGER.info("@@@@@ RpcService Annotation id={} , bean={}" , key , object.getClass());
            });
        }


        AbstractRpcServer abstractRpcServer = context.getBean(AbstractRpcServer.class);
        LOGGER.info("scan AbstractRpcServer result is {}" , abstractRpcServer);
        // 启动RPC 服务
        if (abstractRpcServer != null) {
            abstractRpcServer.start();
        }
    }

    /**
     * 解析rpc服务信息
     * @param clazz rpc 服务类
     * @param target 目标类
     * @return
     */
    private RpcServiceMeta resolveRpcServiceMeta(Class clazz , Object target) {
        Map<String , List<RpcMethodHandler>> stringListMap = new HashMap<>();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(RpcMethod.class)) {
                RpcMethodHandler rpcMethodHandler = new RpcMethodHandler(target , method.getName() , method , method.getParameterTypes());
                if (stringListMap.containsKey(method.getName())) {
                    stringListMap.get(method.getName()).add(rpcMethodHandler);
                } else {
                    List<RpcMethodHandler> rpcMethodHandlers = new ArrayList<>();
                    rpcMethodHandlers.add(rpcMethodHandler);
                    stringListMap.put(method.getName() , rpcMethodHandlers);
                }
            }
        }

        return new RpcServiceMeta(clazz , stringListMap);
    }
}
