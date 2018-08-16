package com.xiaogch.rpc.meta;

import java.util.Objects;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/23 15:34 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public enum  ServiceEnum {

    TEST("test_service" , "测试服务"),
    WECHAT("wechat_user" , "微信用户服务"),
    UNKNOWN("unknown" , "未知服务");

    public final String code;
    public final String name;

    ServiceEnum(String code, String name) {
        this.name = name;
        this.code = code;
    }

    public static ServiceEnum getServiceEnum(String code) {
        ServiceEnum[] serviceEnums = ServiceEnum.values();
        for (ServiceEnum serviceEnum : serviceEnums) {
            if (Objects.equals(serviceEnum.code , code)) {
                return serviceEnum;
            }
        }
        return UNKNOWN;
    }
}
