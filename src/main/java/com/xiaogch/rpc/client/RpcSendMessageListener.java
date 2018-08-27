package com.xiaogch.rpc.client;

import com.xiaogch.rpc.RpcException;
import com.xiaogch.rpc.RpcResponse;
import org.jboss.netty.buffer.ChannelBuffer;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/22 19:11 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public interface RpcSendMessageListener {

    /**
     * This will be called when the request has successfully been written to the transport
     * layer (e.g. socket)
     */
    void onRequestSent();

    /**
     * This will be called when a full response to the request has been received
     *
     * @param responseMessage The response
     */
    void onResponseReceived(RpcResponse responseMessage);

    /**
     * This will be called if the channel encounters an error before the request is sent or
     * before a response is received
     *
     * @param requestException A {@link RpcException} describing the problem that was encountered
     */
    void onChannelError(RpcException requestException);
}
