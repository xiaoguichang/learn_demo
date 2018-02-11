package com.xiaogch.common.http.response;

/**
 * Created by Administrator on 2018/2/4 0004.
 */
public enum ResponseCode {

    /** 成功 */
    SUCCESS(10000 , "success"),

    /** 系统错误 */
    SYS_ERROR(99999, "system error");

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
