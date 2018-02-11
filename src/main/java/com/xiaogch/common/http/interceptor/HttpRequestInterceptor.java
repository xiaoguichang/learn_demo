package com.xiaogch.common.http.interceptor;

import com.xiaogch.common.http.RequestContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * Created by Administrator on 2018/2/4 0004.
 */
public class HttpRequestInterceptor implements HandlerInterceptor {

    static Logger logger = LoggerFactory.getLogger(HttpRequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Enumeration<String> parameters = request.getParameterNames();
        StringBuilder builder = new StringBuilder();
        RequestContextHolder.setRequestUrl(request.getRequestURI());
        int index = 0;
        if (parameters.hasMoreElements())  {
            String parameter = parameters.nextElement();
            if (index > 0) {
                builder.append("&").append(parameter).append("=").append(request.getParameter(parameter));
            } else {
                builder.append(parameter).append("=").append(request.getParameter(parameter));
            }
            index ++;
        }
        RequestContextHolder.setRequestParameter(builder.toString());
        RequestContextHolder.setBeginTime(System.currentTimeMillis());

        logger.info(" HttpRequestInterceptor ...... perHandle ");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
