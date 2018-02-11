package com.xiaogch.common.http.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


public class IpAddressUtil {

    static Logger logger = LoggerFactory.getLogger(IpAddressUtil.class);

    /**
     *
     * 功能描述：获取请求ip信息
     *
     * @param request
     *
     * @author xiaoguichang
     *
     * @since 2017年7月11日
     *
     * @updata:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    public static String getRequestIp(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            logger.info("header={} , value={}" , header , request.getHeader(header));
        }

        String srcIp = request.getHeader("X-Real-IP");
        if (!isInnerIp(srcIp) && StringUtils.hasText(srcIp) && !"unknown".equalsIgnoreCase(srcIp)) {
            return srcIp;
        }

        srcIp = request.getHeader("x-forwarded-for");
        if (StringUtils.hasText(srcIp) && !"unknown".equalsIgnoreCase(srcIp)) {
            String ips[] = srcIp.split(",");
            for (String ip : ips) {
                if (!isInnerIp(ip) && !"unknown".equalsIgnoreCase(ip)) {
                    return ip;
                }
            }

        }
        srcIp = request.getHeader("Proxy-Client-IP");
        if (!isInnerIp(srcIp) && StringUtils.hasText(srcIp) && !"unknown".equalsIgnoreCase(srcIp)) {
            return srcIp;
        }

        srcIp = request.getHeader("WL-Proxy-Client-IP");
        if (!isInnerIp(srcIp) && StringUtils.hasText(srcIp) && !"unknown".equalsIgnoreCase(srcIp)) {
            return srcIp;
        }

        srcIp = request.getHeader("HTTP_CLIENT_IP");
        if (!isInnerIp(srcIp) && StringUtils.hasText(srcIp) && !"unknown".equalsIgnoreCase(srcIp)) {
            return srcIp;
        }

        srcIp = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (!isInnerIp(srcIp) && StringUtils.hasText(srcIp) && !"unknown".equalsIgnoreCase(srcIp)) {
            return srcIp;
        }
        return request.getRemoteAddr();
    }

    public static boolean isInnerIp(String ip) {
        return "127.0.0.1".equals(ip);
    }
}
