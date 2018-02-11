package com.xiaogch.common.http;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.xiaogch.common.http.response.Response;
import com.xiaogch.common.http.response.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/2/5 0005.
 */

@ControllerAdvice
public class RestControllerExceptionHandler {

    @Autowired
    FastJsonConfig fastJsonConfig;

    static Logger logger = LoggerFactory.getLogger(RestControllerExceptionHandler.class);


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object unknownExceptionHandler(Exception e) {
        return exceptionHandle(e);
    }

    //运行时异常
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Object runtimeExceptionHandler(RuntimeException e) {
        return exceptionHandle(e);
    }

    public Object exceptionHandle(Exception e) {
        logger.error(e.getMessage(), e);
        Response response = new Response(ResponseCode.SYS_ERROR);
        response.setMsg(e.getClass().getName());

        String responseString = JSONObject.toJSONStringWithDateFormat(response ,
                fastJsonConfig.getDateFormat(),
                fastJsonConfig.getSerializerFeatures());

        logger.info("{}\t{}\t{}\t{}ms", RequestContextHolder.getRequestUrl(),
                RequestContextHolder.getRequestParameter(), responseString ,
                System.currentTimeMillis() - RequestContextHolder.getBeginTime());
        return response;
    }
}
