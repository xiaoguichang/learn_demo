package com.xiaogch.rpc;

import com.xiaogch.rpc.meta.HostAndPort;
import com.xiaogch.rpc.service.HelloService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Executors;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/17 17:34 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class RpcTest {

    public static void main(String[] args) {
        try {
            Logger logger = LogManager.getLogger(RpcTest.class);
            HostAndPort hostAndPort = new HostAndPort("127.0.0.1" , 10001);
            HelloService helloService = RpcServiceClient.getInstance().getService(hostAndPort , HelloService.class);
            String result = helloService.sayHello();
            logger.info("helloService.sayHello() result is {}" ,  result);

            result = helloService.sayHelloByName("肖贵长");
            logger.info("helloService.sayHelloByName() result is {}" ,  result);

            result = helloService.sayHello();
            logger.info("helloService.sayHello() result is {}" ,  result);

            result = helloService.sayHelloByName("肖贵长");
            logger.info("helloService.sayHelloByName() result is {}" ,  result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

//class A implements InvocationHandler {
//
//    @Override
//    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        System.out.println("888888888888888888");
//        return null;
//    }
//}
