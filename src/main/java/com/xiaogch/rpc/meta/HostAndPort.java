package com.xiaogch.rpc.meta;

import org.springframework.util.Assert;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/23 15:46 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class HostAndPort {

    /** Hostname, IPv4/IPv6 literal, or unvalidated nonsense. */
    private String host;

    /** Validated port number in the range [0..65535], or NO_PORT */
    private int port;

    public HostAndPort() {

    }

    public HostAndPort(String host, int port) {
        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException("invalid port , port must equal or great than 0 and must equal or litter than 65535");
        }
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public static HostAndPort from(String hostAndPort) {
        Assert.hasText(hostAndPort , "hostAndPort can't be null or empty");
        if (hostAndPort.indexOf(":") > -1) {
            String[] array = hostAndPort.split(":");
            if (array.length != 2) {
                throw new IllegalArgumentException("invalid hostAndPort , hostAndPort format is ip:port");
            }
            try {
                return new HostAndPort(array[0] , Integer.parseInt(array[1]));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("invalid port , port must be int and equal or great than 0 and must equal or litter than 65535" , e);
            }

        } else {
            throw new IllegalArgumentException("invalid hostAndPort , hostAndPort format is ip:port");
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HostAndPort{");
        sb.append("host='").append(host).append('\'');
        sb.append(", port=").append(port);
        sb.append('}');
        return sb.toString();
    }
}
