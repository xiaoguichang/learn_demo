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
public enum ServiceMode {
    WHITE("白度"), GRAY("灰度");

    public final String name;

    ServiceMode(String name) {
        this.name = name;
    }

}
