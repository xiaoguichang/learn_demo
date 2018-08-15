package com.xiaogch.conf.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/2/4 0004.
 */
@Component
@Aspect
public class DatasourceSelectAop {

    public DatasourceSelectAop() {
        logger.info("DatasourceSelectAop has initialed ....");
    }

    static Logger logger = LogManager.getLogger(DatasourceSelectAop.class);

    @Pointcut("execution(* com.xiaogch..*.dao..*.select*(..)) || execution(* com.xiaogch..*.dao..*.get*(..))")
    public void pointCutSelect() {

    }

    @Around(value = "pointCutSelect()")
    public Object dealWithAroundForPointCutSelect(ProceedingJoinPoint joinPoint) {
        DatasourceContextHolder.selectSlave();
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        } finally {
            DatasourceContextHolder.clearDatasourceType();
        }

    }


    @Pointcut("execution(* com.xiaogch..*.dao..*.insert*(..)) " +
            "|| execution(* com.xiaogch..*.dao..*.update*(..)) " +
            "|| execution(* com.xiaogch..*.dao..*.delete*(..))")
    public void pointCutUpdate() {

    }

    @Around(value = "pointCutUpdate()")
    public Object dealWithAroundForointCutUpdate(ProceedingJoinPoint joinPoint) {
        DatasourceContextHolder.selectMaster();
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        } finally {
            DatasourceContextHolder.clearDatasourceType();
        }
    }

}
