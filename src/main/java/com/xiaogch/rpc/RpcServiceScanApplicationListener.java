package com.xiaogch.rpc;

import com.xiaogch.rpc.annotation.RpcService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;

import java.util.Map;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/15 19:21 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class RpcServiceScanApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

    static Logger LOGGER = LogManager.getLogger(RpcServiceScanApplicationListener.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ApplicationContext context = event.getApplicationContext();
        Map<String , Object> beans = context.getBeansWithAnnotation(RpcService.class);
        LOGGER.info("@@@@@ get beans for RpcService Annotation beans size is {}" , beans == null ? 0 : beans.size());
        if (beans != null) {
            beans.forEach((String key , Object object) -> {
                Class clazz = object.getClass();
                Class[] interfaces = clazz.getInterfaces();
                if (interfaces != null && interfaces.length > 0) {
                    for (Class tempClazz : interfaces) {

                        LOGGER.info("@@@@@ RpcService Annotation tempClazz={}" , tempClazz.getClass());
                    }
                }

                LOGGER.info("@@@@@ RpcService Annotation id={} , bean={}" , key , object.getClass());
            });
        }
    }
}
