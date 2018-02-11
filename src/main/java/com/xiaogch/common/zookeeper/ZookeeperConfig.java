package com.xiaogch.common.zookeeper;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/2/8 0008.
 */

@Component
@ConfigurationProperties(prefix = "spring.zookeeper")
public class ZookeeperConfig {

    private String serverPath;

    private String connectStr;

    private int sessionTimeout;

    private int connectTimeout;

    private int reconnectTimes;

    public String getConnectStr() {
        return connectStr;
    }

    public void setConnectStr(String connectStr) {
        this.connectStr = connectStr;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReconnectTimes() {
        return reconnectTimes;
    }

    public void setReconnectTimes(int reconnectTimes) {
        this.reconnectTimes = reconnectTimes;
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    @Override
    public String toString() {
        return "ZookeeperConfig{" +
                "serverPath='" + serverPath + '\'' +
                ", connectStr='" + connectStr + '\'' +
                ", sessionTimeout=" + sessionTimeout +
                ", connectTimeout=" + connectTimeout +
                ", reconnectTimes=" + reconnectTimes +
                '}';
    }
}
