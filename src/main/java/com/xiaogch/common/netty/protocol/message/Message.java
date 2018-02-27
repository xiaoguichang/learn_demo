package com.xiaogch.common.netty.protocol.message;

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
public class Message {

    /** 消息头 */
    private Header header;

    /** 消息体 */
    private Object body;

    public Header getHeader() {
        return header;
    }

    public Message setHeader(Header header) {
        this.header = header;
        return this;
    }

    public Object getBody() {
        return body;
    }

    public Message setBody(Object body) {
        this.body = body;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Message{");
        sb.append("header=").append(header);
        sb.append(", body=").append(body);
        sb.append('}');
        return sb.toString();
    }
}
