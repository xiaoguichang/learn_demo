package com.xiaogch.common.netty.protocol.message;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/2/28 11:38 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public enum MessageType {

    /** 0：业务请求消息 */
    BUSI_REQ((byte)0),

    /** 1：业务响应消息 */
    BUSI_RESP((byte)1),
    
    /** 2：业务ONE WAY 消息（既是请求也是响应）*/
    BUSI_ONEWAY((byte)2),

    /** 3：握手请求消息*/
    HANDSHAKE_REQ((byte)3),

    /** 4：握手响应消息 */
    HANDSHAKE_RESP((byte)4),

    /** 5：心跳请求消息（PING）*/
    HEARTBEAT_REQ((byte)5),

    /** 6：心跳响应消息（PONG） */
    HEARTBEAT_RESP((byte)6);

    private byte value;

    MessageType(byte value) {
        this.value = value;
    }

    public byte value() {
        return value;
    }
}
