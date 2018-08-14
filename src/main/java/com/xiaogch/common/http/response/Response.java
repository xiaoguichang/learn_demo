package com.xiaogch.common.http.response;

import org.omg.CORBA.OBJ_ADAPTER;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/2/4 0004.
 */
public class Response implements Serializable {

    private int code;

    private String msg;

    private Object data;

    public Response() {

    }

    public Response(int code , String message , Object data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }

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

    public static Response buildSuccessRsp(){
        return new Response(ResponseCode.SUCCESS.getCode() ,
                ResponseCode.SUCCESS.getMsg() , new Object());
    }

    public static Response buildSuccessRsp(Object data){
        return new Response(ResponseCode.SUCCESS.getCode() ,
                ResponseCode.SUCCESS.getMsg() ,data == null ? new Object() : data);
    }

    public static Response buildFailureRsp(ResponseCode rspCode){
        return buildRsp(rspCode.getCode() , rspCode.getMsg() , new Object());
    }

    public static Response buildFailureRsp(ResponseCode rspCode , Object data){
        return buildRsp(rspCode.getCode() , rspCode.getMsg() , data == null ? new Object() : data);
    }

    public static Response buildFailureRsp(ResponseCode rspCode , String msg){
        return buildRsp(rspCode.getCode() , msg , new Object());
    }

    public static Response buildFailureRsp(ResponseCode rspCode , String msg , Object data){
        return buildRsp(rspCode.getCode() , msg , data == null ? new Object() : data);
    }

    public static Response buildRsp(int code , String msg , Object data){
        return new Response(code , msg , data);
    }


}
