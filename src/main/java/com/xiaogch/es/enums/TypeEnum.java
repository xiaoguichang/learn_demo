package com.xiaogch.es.enums;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/2 19:59 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public enum TypeEnum {

    /** 测试用*/
    TEST("test");

    public String value;

    TypeEnum(String value) {
        this.value = value;
    }
}
