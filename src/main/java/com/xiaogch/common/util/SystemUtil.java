package com.xiaogch.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/20 10:15 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class SystemUtil {

    private static Logger LOGGER = LogManager.getLogger(SystemUtil.class);

    /**
     * 获取当前的进程id
     * @return
     */
    public static int getPid() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        // pid@计算机名
        String name = runtimeMXBean.getName();
        LOGGER.info("runtimeMXBean name={}" , name);
        String[] values = name.split("@");
        return Integer.parseInt(values[0]);
    }

    /**
     * 获取
     * @return
     */
    public static String getIpAddress() {

        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                return inetAddress.getHostAddress();
            }
        } catch (UnknownHostException e) {
            LOGGER.warn("Unable to retrieve localhost");
        }

        InetAddress result = null;
        try {
            int lowest = Integer.MAX_VALUE;
            for (Enumeration<NetworkInterface> nics = NetworkInterface
                    .getNetworkInterfaces(); nics.hasMoreElements();) {
                NetworkInterface ifc = nics.nextElement();
                if (ifc.isUp()) {
                    if (ifc.getIndex() < lowest || result == null) {
                        lowest = ifc.getIndex();
                    } else if (result != null) {
                        continue;
                    }

                    for (Enumeration<InetAddress> addrs = ifc
                            .getInetAddresses(); addrs.hasMoreElements();) {
                        InetAddress address = addrs.nextElement();
                        if (address instanceof Inet4Address && !address.isLoopbackAddress()) {
                            result = address;
                        }
                    }
                }
            }
        } catch (IOException ex) {
            LOGGER.error("Cannot get first non-loopback address", ex);
        }

        if (result != null) {
            return result.getHostAddress();
        }
        return null;
    }

}
