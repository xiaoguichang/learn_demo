package com.xiaogch.common.http.response;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/2/4 0004.
 */
public class Response implements Serializable {

    private Integer code;

    private String msg;

    private Object data;

    public Response() {
        this(ResponseCode.SUCCESS , new Object());
    }

    public Response(Object data) {
        this(ResponseCode.SUCCESS , data);
    }

    public Response(ResponseCode responseResult , Object data) {
        this(responseResult.getCode() , responseResult.getMsg()  , data);
    }

    public Response(Integer code, String msg , Object data) {
        this.code = code;
        this.msg = msg;
        if (data == null) {
            this.data = new Object();
        } else {
            this.data = data;
        }
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        if (data == null) {
            this.data = new Object();
        } else {
            this.data = data;
        }
    }
}
