package com.xiaogch.conf.database;

import org.apache.logging.log4j.LogManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.apache.logging.log4j.Logger;
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

    @Before(value = "pointCutSelect()")
    public void setSalveDatasource(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        logger.info("signature.getModifiers() {}", signature.getModifiers());
        logger.info("signature.getName() {}", signature.getName());
        logger.info("signature.getDeclaringTypeName() {}", signature.getDeclaringTypeName());
        logger.info("signature.getDeclaringType() {}", signature.getDeclaringType());
        logger.info("signature.getClass() {}", signature.getClass());

        logger.info("joinPoint.getKind() {}", joinPoint.getKind());
        logger.info("joinPoint.getTarget() {}", joinPoint.getTarget());
        logger.info("joinPoint.getArgs() {}", joinPoint.getArgs());
        logger.info("joinPoint.getSourceLocation() {}", joinPoint.getSourceLocation());
        logger.info("joinPoint.getStaticPart() {}", joinPoint.getStaticPart());

        DatasourceContextHolder.selectSlave();
    }


    @Pointcut("execution(* com.xiaogch..*.dao..*.insert*(..)) " +
            "|| execution(* com.xiaogch..*.dao..*.update*(..)) " +
            "|| execution(* com.xiaogch..*.dao..*.delete*(..))")
//            "|| execution(* com.xiaogch.springboot.mapper..*.insert*(..)) " +
//            "|| execution(* com.xiaogch.springboot.mapper..*.update*(..)) " +
//            "|| execution(* com.xiaogch.springboot.mapper..*.delete*(..)))")
    public void pointCutUpdate() {

    }

    @Before(value = "pointCutUpdate()")
    public void setMasterDatasource(JoinPoint joinPoint) {
        DatasourceContextHolder.selectMaster();
    }

    @After(" pointCutUpdate() || pointCutSelect()")
    public void clearDatasource() {
        DatasourceContextHolder.clearDatasourceType();
    }
}
