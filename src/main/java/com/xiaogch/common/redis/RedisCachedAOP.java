package com.xiaogch.common.redis;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/4/4 19:19 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */

@Aspect
public class RedisCachedAOP {

    @Pointcut(value = "@annotation(com.xiaogch.common.redis.annotation.RedisCached)")
    public void pointcut() {

    }

    @After(value = "pointcut()")
    public void buildCache(JoinPoint joinPoint){

    }
}
