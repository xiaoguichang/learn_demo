package com.xiaogch.common.netty.protocol.message;

import java.util.HashMap;
import java.util.Map;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/2/27 14:03 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public final class Header {

    /**
     * 消息校验码，由三部分组成：
     * 1）0xabef，固定值标识netty协议消息2个字节；
     * 2）主版本号，1-255，一个字节
     * 3）次版本号，1-255，一个字节
     * crcCode = 0xabef + 主版本号 + 次版本号
     */
    private int crcCode = 0xabef0101;

    /** 消息长度 */
    private int length ;

    /** 会话id*/
    private long sessionId;

    /**
     * 消息类型，取值如下：
     * 0：业务请求消息；
     * 1：业务响应消息；
     * 2：业务ONE WAY 消息（既是请求也是响应）；
     * 3：握手请求消息；
     * 4：握手响应消息；
     * 5：心跳请求消息（PING）；
     * 6：心跳响应消息（PONG）；
     */
    private byte type;

    /** 消息优先级 0-255 */
    private byte priority;

    /** 自定义 */
    private Map<String , Object> attachment = new HashMap<>();

    public int getCrcCode() {
        return crcCode;
    }

    public Header setCrcCode(int crcCode) {
        this.crcCode = crcCode;
        return this;
    }

    public int getLength() {
        return length;
    }

    public Header setLength(int length) {
        this.length = length;
        return this;
    }

    public long getSessionId() {
        return sessionId;
    }

    public Header setSessionId(long sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public byte getType() {
        return type;
    }

    /**
     * @param type 消息类型，取值如下：
     * 0：业务请求消息；
     * 1：业务响应消息；
     * 2：业务ONE WAY 消息（既是请求也是响应）；
     * 3：握手请求消息；
     * 4：握手响应消息；
     * 5：心跳请求消息（PING）；
     * 6：心跳响应消息（PONG）；
     */
    public Header setType(byte type) {
        this.type = type;
        return this;
    }

    public byte getPriority() {
        return priority;
    }

    public Header setPriority(byte priority) {
        this.priority = priority;
        return this;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public Header setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Header{");
        sb.append("crcCode=").append(crcCode);
        sb.append(", length=").append(length);
        sb.append(", sessionId=").append(sessionId);
        sb.append(", type=").append(type);
        sb.append(", priority=").append(priority);
        sb.append(", attachment=").append(attachment);
        sb.append('}');
        return sb.toString();
    }
}
