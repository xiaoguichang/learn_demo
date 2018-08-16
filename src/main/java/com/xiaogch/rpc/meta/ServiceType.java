package com.xiaogch.rpc.meta;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/23 15:22 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public enum  ServiceType {

    WEB("web服务"), RPC("rpc服务");

    public final String name;

    ServiceType(String name) {
        this.name = name;
    }

}
