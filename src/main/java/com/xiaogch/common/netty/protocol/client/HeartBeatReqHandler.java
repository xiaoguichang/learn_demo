package com.xiaogch.common.netty.protocol.client;

import com.xiaogch.common.netty.protocol.message.Header;
import com.xiaogch.common.netty.protocol.message.Message;
import com.xiaogch.common.netty.protocol.message.MessageType;
import io.netty.channel.*;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/2/28 13:41 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class HeartBeatReqHandler extends ChannelInboundHandlerAdapter {

    private volatile ScheduledFuture<?> heartBeatTask;

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
        Message reqMsg = (Message) msg;

        if (reqMsg != null && reqMsg.getHeader() != null
                && reqMsg.getHeader().getType() == MessageType.HANDSHAKE_RES.value()) {
            System.out.println("client receive server handshake response , message is : " + reqMsg);
            heartBeatTask = ctx.executor().scheduleAtFixedRate(new HeartBeatTask(ctx) , 0 , 5000 , TimeUnit.MILLISECONDS);
        } else if (reqMsg != null && reqMsg.getHeader() != null
                && reqMsg.getHeader().getType() == MessageType.HEARTBEAT_RES.value()) {
            System.out.println("client receive server heartbeat response , message is : " + reqMsg);
        } else {
            ctx.fireChannelRead(ctx);
        }

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
        if (heartBeatTask != null) {
            heartBeatTask.cancel(true);
            heartBeatTask = null;
        }
        ctx.fireExceptionCaught(cause);
    }

    class HeartBeatTask implements Runnable {

        ChannelHandlerContext ctx;

        HeartBeatTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }
        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            Message heartBeatReqMsg = new Message();
            Header header = new Header();
            header.setType(MessageType.HEARTBEAT_REQ.value());
            heartBeatReqMsg.setHeader(header);
            System.out.println("client send heart beat request to server , message is : " +  heartBeatReqMsg);
            ctx.writeAndFlush(heartBeatReqMsg);
        }
    }


}
