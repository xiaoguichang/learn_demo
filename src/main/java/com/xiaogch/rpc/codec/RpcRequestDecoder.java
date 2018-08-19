package com.xiaogch.rpc.codec;

import com.xiaogch.rpc.RpcRequest;
import com.xiaogch.rpc.util.SerializedUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;

import java.io.IOException;
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
public class RpcRequestDecoder extends LengthFieldBasedFrameDecoder {


    public RpcRequestDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) throws IOException {
        super(maxFrameLength , lengthFieldOffset , lengthFieldLength);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        byte[] requestData = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(requestData);
        RpcRequest rpcRequest = SerializedUtil.deserializeByProtostuff(requestData , RpcRequest.class);
        return rpcRequest;
    }
}