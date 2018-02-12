package com.xiaogch.common.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * Created by Administrator on 2018/2/12 0012.
 */
public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        final int byteSize = byteBuf.readableBytes();
        byte[] bytes = new byte[byteSize];
        byteBuf.readBytes(bytes , 0 , byteSize);
        MessagePack messagePack = new MessagePack();
        list.add(messagePack.read(bytes));
    }
}
