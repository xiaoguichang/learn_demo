package com.xiaogch.common.http;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/2/3 22:05 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
@Component
@Aspect
public class RequestLogAOP {

    @Autowired
    FastJsonConfig fastJsonConfig;

    static Logger logger = LogManager.getLogger(RequestLogAOP.class);

    public RequestLogAOP(){
    }

    @Pointcut(value = "@annotation(org.springframework.web.bind.annotation.RequestMapping) " +
            "&& execution(* com.xiaogch.web..*.*Controller.*(..))")
    public void pointcutException(){

    }

    @AfterReturning(value = "pointcutException()" , returning="returnValue")
    public void after(JoinPoint joinPoint , Object returnValue){
        String response = JSONObject.toJSONStringWithDateFormat(returnValue ,
                fastJsonConfig.getDateFormat(),
                fastJsonConfig.getSerializerFeatures());

        logger.info("{}\t{}\t{}\t{}ms", RequestContextHolder.getRequestUrl(),
                RequestContextHolder.getRequestParameter(), response ,
                System.currentTimeMillis() - RequestContextHolder.getBeginTime());
    }
}
