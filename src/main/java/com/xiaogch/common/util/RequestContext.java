package com.xiaogch.common.util;

import com.xiaogch.gateway.http.GatewayHttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/5 16:45 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class RequestContext extends ConcurrentHashMap<String , Object> {

    private static Class<? extends RequestContext> contextClass = RequestContext.class;

    private static final ThreadLocal<? extends RequestContext> threadLocal = new ThreadLocal<RequestContext>() {
        @Override
        protected RequestContext initialValue() {
            try {
                return contextClass.newInstance();
            } catch (Throwable e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    };

    /**
     * 获取请求上下文
     * @return
     */
    public static RequestContext concurrentRequestContext(){
        return threadLocal.get();
    }

    public GatewayHttpRequest getGatewayHttpRequest() {
        return (GatewayHttpRequest) this.get("httpRequest");
    }

    public void setGatewayHttpRequest(GatewayHttpRequest httpRequest){
        this.put("httpRequest" , httpRequest);
    }


    public void setRequestBeginTime(Long value) {
        this.put("requestBeginTime" , value);
    }

    public Long getRequestBeginTime() {
        return (Long) this.get("requestBeginTime");
    }

    public HttpServletRequest getHttpServletRequest() {
        return (HttpServletRequest) this.get("httpServletRequest");
    }

    public void setHttpServletRequest(HttpServletRequest value) {
        this.put("httpServletRequest" , value);
    }

    public void unset() {
        threadLocal.remove();
    }
}
