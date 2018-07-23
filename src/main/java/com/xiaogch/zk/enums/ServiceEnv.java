package com.xiaogch.zk.enums;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/23 15:24 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public enum ServiceEnv {

    DEVP("开发") , TEST("测试") , PROD("生产");

    public final String name;
    ServiceEnv(String name) {
        this.name = name;
    }
}
