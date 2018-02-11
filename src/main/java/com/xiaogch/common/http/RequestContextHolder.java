package com.xiaogch.common.http;

/**
 * ProjectName: wechat-management-parent<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: wechat-management-parent <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/2/3 21:58 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class RequestContextHolder {
    static ThreadLocal<String> requestUrl = new ThreadLocal<>();
    static ThreadLocal<String> requestParameter = new ThreadLocal<>();
    static ThreadLocal<Long> beginTime = new ThreadLocal<>();

    public static String getRequestUrl() {
        return requestUrl.get();
    }

    public static void setRequestUrl(String value) {
        requestUrl.set(value);
    }

    public static String getRequestParameter() {
        return requestParameter.get();
    }

    public static void setRequestParameter(String value) {
        requestParameter.set(value);
    }

    public static void setBeginTime(Long value) {
        beginTime.set(value);
    }

    public static Long getBeginTime() {
        return beginTime.get();
    }


}
