package com.xiaogch.common.netty.protocol.client;

import com.xiaogch.common.netty.protocol.message.Header;
import com.xiaogch.common.netty.protocol.message.Message;
import com.xiaogch.common.netty.protocol.message.MessageType;
import io.netty.channel.*;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/2/28 10:58 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class LoginReqHandler extends ChannelInboundHandlerAdapter {

    /**
     * Calls {@link ChannelHandlerContext#fireChannelActive()} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("send login request begin ... ");
        ctx.writeAndFlush(buildLoginReqMessage());
        System.out.println("send login request end ... ");
    }

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
//        super.channelRead(ctx, msg);
        Message message = (Message) msg;
        System.out.println("LoginReqHandler " + message);
        if (message != null && message.getHeader() != null
                && message.getHeader().getType() == MessageType.HANDSHAKE_RESP.value()) {
            byte loginResult = (byte) message.getBody();
            if (loginResult != (byte) 0) {
                // 握手失败
                ctx.close();
            } else {
                System.out.println("Login ok : " + message);
                ctx.fireChannelRead(msg);
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    public Message buildLoginReqMessage() {
        Message message = new Message();
        Header header = new Header();
        header.setType(MessageType.HANDSHAKE_REQ.value());
        message.setHeader(header);
        return message;
    }

    /**
     * Calls {@link ChannelHandlerContext#fireExceptionCaught(Throwable)} to forward
     * to the next {@link ChannelHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }
}
