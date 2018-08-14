package com.xiaogch.common.redis;

import com.xiaogch.common.redis.annotation.RedisDatasource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/14 17:31 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
@Aspect
public class RedisDatasourceSelectAop {

    @Pointcut("@annotation(com.xiaogch.common.redis.annotation.RedisDatasource)")
    public void pointcutForRedisDynamicDatasource(){

    }

    @Around("pointcutForRedisDynamicDatasource()")
    public Object dealWithAround(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        RedisDatasource redisDatasource = method.getAnnotation(RedisDatasource.class);
        RedisDatasourceFactory.setRedisDataSourceName(redisDatasource.value());
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        } finally {
            RedisDatasourceFactory.unsetRedisDataSourceName();
        }
    }
}
