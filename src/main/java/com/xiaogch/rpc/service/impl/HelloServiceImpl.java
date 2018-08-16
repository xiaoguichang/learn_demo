package com.xiaogch.rpc.service.impl;

import com.xiaogch.rpc.service.HelloService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/15 17:26 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
@Service
public class HelloServiceImpl implements HelloService {

    static Logger LOGGER = LogManager.getLogger(HelloServiceImpl.class);
    @Override
    public String sayHello() {
        return "hello !";
    }

    @Override
    public String sayHelloByName(String name) {
        return "hello " + name + " !";
    }

    public static void main(String[] args) {
       Method[] methods = HelloServiceImpl.class.getDeclaredMethods();
       for (Method method : methods) {
           LOGGER.info("==============================");
           LOGGER.info("method name={}" , method.getName());
           LOGGER.info("method return type={}" , method.getReturnType().getName());
           LOGGER.info("method parameter types={}" , method.getParameterTypes());
//           LOGGER.info("method return type={}" , method.getReturnType().getName());
//           LOGGER.info("method return type={}" , method.getReturnType().getName());
           LOGGER.info("==============================\n");


       }
    }
}