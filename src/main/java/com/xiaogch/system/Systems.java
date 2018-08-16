package com.xiaogch.system;

/**
 * 系统枚举类
 * add by xiaogch 2018-04-16
 */
public enum Systems {

    TEST("system:test");

    private String code;

    Systems(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}