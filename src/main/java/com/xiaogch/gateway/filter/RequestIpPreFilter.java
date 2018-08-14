package com.xiaogch.gateway.filter;

import com.xiaogch.common.util.RequestContext;
import com.xiaogch.gateway.http.GatewayHttpRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/5 18:51 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class RequestIpPreFilter implements GatewayFilter {

    private static Logger LOGGER = LogManager.getLogger(RequestIpPreFilter.class);

    @Override
    public int getFilterOrder() {
        return 0;
    }

    @Override
    public Object doFilter() {
        RequestContext context = RequestContext.concurrentRequestContext();
        GatewayHttpRequest gatewayHttpRequest = context.getGatewayHttpRequest();
        LOGGER.info("RequestIpPreFilter request={}" , gatewayHttpRequest );
        String ip = getIpAddr(gatewayHttpRequest);
        LOGGER.info("request ip is:{}" , ip);
        if (!"127.0.0.1".equals(ip)) {
            LOGGER.info("ip {} not in white list", ip);
        }
        return null;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.PRE;
    }

    /**
     * 获取Ip地址
     * @param request
     * @return
     */
    public String getIpAddr(GatewayHttpRequest request){
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}
