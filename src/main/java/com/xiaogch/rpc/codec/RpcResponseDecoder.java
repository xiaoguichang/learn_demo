package com.xiaogch.rpc.codec;

import com.xiaogch.rpc.RpcRequest;
import com.xiaogch.rpc.RpcResponse;
import com.xiaogch.rpc.util.SerializedUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.io.IOException;
import java.util.List;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/17 17:41 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class RpcResponseDecoder extends LengthFieldBasedFrameDecoder {

    public RpcResponseDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) throws IOException {
        super(maxFrameLength , lengthFieldOffset , lengthFieldLength);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        byte[] requestData = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(requestData);
        RpcResponse response = SerializedUtil.deserializeByProtostuff(requestData , RpcResponse.class);
        return response;
    }

}
