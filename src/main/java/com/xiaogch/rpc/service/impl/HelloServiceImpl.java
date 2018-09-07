package com.xiaogch.rpc.service.impl;

import com.xiaogch.rpc.service.HelloService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

        LOGGER.info("sayHello() be called ...");
        return "hello !";
    }

    @Override
    public String sayHelloByName(String name , Integer age , Date birthDay , List<String> addresses) {
        LOGGER.info("sayHelloByName() be called ...");
        LOGGER.info("name={} age={} birthDay={} addresses={}" , name, age , birthDay , addresses);
        return "hello " + name + " !";
    }

    @Override
    public Integer getAge(String name) {
        LOGGER.info("name={} age is null", name);
        return null;
    }


}
