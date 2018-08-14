package com.xiaogch.web.aop;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.xiaogch.common.http.response.Response;
import com.xiaogch.common.http.response.ResponseCode;
import com.xiaogch.common.util.RequestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;

@Aspect
@Component
public class RequestMappingAop {

    static Logger LOGGER = LogManager.getLogger(RequestMappingAop.class);

    @Autowired
    FastJsonConfig fastJsonConfig;


    RequestMappingAop(){
        LOGGER.info("RequestMappingAop init ......");
    }

    @Pointcut(value = "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void pointcutRequestMapping() {
    }

    @Around(value = "pointcutRequestMapping()")
    public Object pointcutArroudDeal(ProceedingJoinPoint joinPoint){
        RequestContext.concurrentRequestContext().setRequestBeginTime(System.currentTimeMillis());
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            RequestContext.concurrentRequestContext().setHttpServletRequest(request);
        }
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Object object;
        try {
            object = joinPoint.proceed();
            String rspJson = JSONObject.toJSONStringWithDateFormat(object , fastJsonConfig.getDateFormat(),
                    fastJsonConfig.getSerializerFeatures());
            writeRequestLog("success" , rspJson);
            return object;
        } catch (Throwable throwable) {
            LOGGER.error(throwable.getMessage() , throwable);
            Response response = Response.buildFailureRsp(ResponseCode.SYS_ERROR , throwable.getClass().getName() , new Object());
            String rspJson = JSONObject.toJSONStringWithDateFormat(response , fastJsonConfig.getDateFormat(),
                    fastJsonConfig.getSerializerFeatures());
            writeRequestLog("exception" , rspJson);
            throw new RuntimeException(throwable);
        } finally {
            RequestContext.concurrentRequestContext().unset();
        }
    }

//    /**
//     * 确认用户信息 , 只针对使用<link>@CheckUserInfo</>
//     * @param method
//     */
//    private void checkUserInfo(Method method){
//        if (method.getAnnotation(CheckUserInfo.class) != null) {
//            LOGGER.info("method={} , do checkUserInfo" , method);
//            // 获取HttpServletRequest
//            HttpServletRequest request = RequestContext.concurrentRequestContext().getHttpServletRequest();
//            if (request != null) {
//                // 获取openId
//                String openId = request.getParameter("openId");
//                LOGGER.info("pointcutArroudDeal openId={}" , openId);
//
//                // 根据openId查询用户信息，如果存在该用户，将用户信息设置在ThreadLocal中
//                if (StringUtils.hasText(openId)) {
//                    Optional<UserInfoDTO> optional = userInfoCache.getUserInfo(openId);
//                    if (optional.isPresent()) {
//                        LOGGER.info("pointcutArroudDeal openId={} , userInfoDTO={}" , openId , optional.get());
//                        RequestContext.concurrentRequestContext().setUserInfoDTO(optional.get());
//                    } else {
//                        LOGGER.info("pointcutArroudDeal openId={} , userInfoDTO not exists" , openId);
//                    }
//                }
//            }
//
//        }
//    }

    private void writeRequestLog(String resultType , String rspJson) {
        RequestContext requestContext = RequestContext.concurrentRequestContext();
        Long requestBeginTime = requestContext.getRequestBeginTime();
        String path = requestContext.getHttpServletRequest() == null ? "" : requestContext.getHttpServletRequest().getRequestURI();
        long now = System.currentTimeMillis();
        LOGGER.info("writeRequestLog: {}\t{}\t{}\t{}ms\t{}", path , getRequestParameter() , rspJson , now - (requestBeginTime == null ?  now : requestBeginTime) , resultType);
    }

    private String getRequestParameter(){
        HttpServletRequest request = RequestContext.concurrentRequestContext().getHttpServletRequest();
        StringBuilder builder = new StringBuilder();
        if (request != null) {
            Enumeration<String> parameters = request.getParameterNames();
            int index = 0;
            while (parameters.hasMoreElements())  {
                String parameter = parameters.nextElement();
                if (index > 0) {
                    builder.append("&").append(parameter).append("=").append(request.getParameter(parameter));
                } else {
                    builder.append(parameter).append("=").append(request.getParameter(parameter));
                }
                index ++;
            }
        }
        return builder.toString();
    }

}