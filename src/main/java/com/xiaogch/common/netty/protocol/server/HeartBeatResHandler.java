package com.xiaogch.common.netty.protocol.server;

import com.xiaogch.common.netty.protocol.message.Header;
import com.xiaogch.common.netty.protocol.message.Message;
import com.xiaogch.common.netty.protocol.message.MessageType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/2/28 14:00 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class HeartBeatResHandler extends ChannelInboundHandlerAdapter {

    /**
     * Calls {@link ChannelHandlerContext#fireChannelRead(Object)} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message requestMsg = (Message) msg;
        if (requestMsg != null && requestMsg.getHeader() != null
                && requestMsg.getHeader().getType() == MessageType.HEARTBEAT_REQ.value()) {
            System.out.println("receive client heart beat request , message is : " + requestMsg);
            Message responseMsg = new Message();
            Header header = new Header();
            header.setType(MessageType.HEARTBEAT_RESP.value());
            responseMsg.setHeader(header);
            System.out.println("send heart beat response to client , message is : " + responseMsg);
            ctx.writeAndFlush(requestMsg);
        } else {
            ctx.fireChannelRead(msg);
        }

    }
}
