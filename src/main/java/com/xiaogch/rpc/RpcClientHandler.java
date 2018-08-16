package com.xiaogch.rpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/16 18:56 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class RpcClientHandler extends ChannelInboundHandlerAdapter {

    AtomicLong atomicLong = new AtomicLong(0);

    private ChannelHandlerContext channelHandlerContext;

    public RpcClientHandler() {
        super();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        channelHandlerContext = ctx;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    public Future<Object> sendRequest(SendRequestCallable sendRequestCallable) {
        return null;
    }

    class SendRequestCallable implements Callable<Object> {

        private RpcRequest rpcRequest;
        private RpcResponse rpcResponse;

        public SendRequestCallable(RpcRequest rpcRequest) {
            this.rpcRequest = rpcRequest;
        }

        /**
         * Computes a result, or throws an exception if unable to do so.
         *
         * @return computed result
         *
         * @throws Exception if unable to compute a result
         */
        @Override
        public Object call() throws Exception {
            channelHandlerContext.writeAndFlush(rpcRequest);
            wait();
            return rpcResponse.getData();
        }

        public void setResult(RpcResponse rpcResponse){
            this.rpcResponse = rpcResponse;
            notify();
        }
    }
}




