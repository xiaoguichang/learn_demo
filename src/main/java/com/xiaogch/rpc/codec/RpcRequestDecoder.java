package com.xiaogch.rpc.codec;

import com.xiaogch.rpc.RpcRequest;
import com.xiaogch.rpc.util.SerializedUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/16 16:21 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class RpcRequestDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byte[] requestData = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(requestData);
        RpcRequest rpcRequest = SerializedUtil.deserializeByProtostuff(requestData , RpcRequest.class);
        list.add(rpcRequest);
    }

}
