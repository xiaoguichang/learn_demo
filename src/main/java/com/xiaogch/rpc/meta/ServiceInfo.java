package com.xiaogch.rpc.meta;


import java.util.Date;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/23 15:20 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class ServiceInfo {

    /** 进程id */
    private Integer pid;

    /** 服务启动时间 */
    private Date startupTime;

    /** 服务模式：白度|灰度 */
    private ServiceMode serviceMode;

    /** 服务环境：开发|测试|生产 */
    private ServiceEnv serviceEnv;

    /** 服务类型：web服务|rpc服务 */
    private ServiceType serviceType;

    /** 服务 */
    private String serviceName;

    /** 服务 */
    private String serviceCode;

    /** 服务ip端口*/
    private HostAndPort hostAndPort;

    public ServiceInfo() {

    }


    public ServiceInfo(ServiceEnum service) {
        this.serviceName = service.name;
        this.serviceCode = service.code;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Date getStartupTime() {
        return startupTime;
    }

    public void setStartupTime(Date startupTime) {
        this.startupTime = startupTime;
    }

    public ServiceMode getServiceMode() {
        return serviceMode;
    }

    public void setServiceMode(ServiceMode serviceMode) {
        this.serviceMode = serviceMode;
    }

    public ServiceEnv getServiceEnv() {
        return serviceEnv;
    }

    public void setServiceEnv(ServiceEnv serviceEnv) {
        this.serviceEnv = serviceEnv;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public HostAndPort getHostAndPort() {
        return hostAndPort;
    }

    public void setHostAndPort(HostAndPort hostAndPort) {
        this.hostAndPort = hostAndPort;
    }
}
