package com.xiaogch.rpc.codec;

import com.xiaogch.rpc.util.SerializedUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/16 18:06 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class RpcCommonEncoder extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        if (o == null) {
            byte[] bytes = new byte[0];
            byteBuf.writeBytes(bytes);
        } else {
            byte[] bytes = SerializedUtil.serializeByProtostuff(o);
            byteBuf.writeBytes(bytes);
        }
    }
}
