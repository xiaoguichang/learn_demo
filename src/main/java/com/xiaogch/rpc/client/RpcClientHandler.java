package com.xiaogch.rpc.client;

import com.xiaogch.rpc.RpcException;
import com.xiaogch.rpc.RpcRequest;
import com.xiaogch.rpc.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.*;
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

    static AtomicLong atomicLong = new AtomicLong(0);

    private static final long timeout = 60000;

    private static ChannelHandlerContext channelHandlerContext;

    private static Map<Long , RpcSendMessageListener> listenerMap = new ConcurrentHashMap<>(1000);

    private static ExecutorService executorService = Executors.newFixedThreadPool(3);


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
        if (msg instanceof RpcResponse) {
            System.out.println("received response .......");
            RpcResponse rpcResponse = (RpcResponse) msg;
            if (rpcResponse != null) {
                if (listenerMap.containsKey(rpcResponse.getRequestId())) {
                    listenerMap.get(rpcResponse.getRequestId()).onResponseReceived(rpcResponse);
                }
            }
        }
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

    public void registerListener(RpcRequest request , RpcSendMessageListener listener) {
        listenerMap.put(request.getRequestId() , listener);
    }

    /**
     * 发送消息
     * @param request 请求实体
     * @param listener 请求监听器
     */
    public void sendMessage(RpcRequest request, RpcSendMessageListener listener) {

        registerListener(request , listener);

        try {
            channelHandlerContext.writeAndFlush(request);
            if (listenerMap.containsKey(request.getRequestId())) {
                listenerMap.get(request.getRequestId()).onRequestSent();
            }
        } catch (Exception e){
            SocketAddress socketAddress = channelHandlerContext.channel().remoteAddress();
            if (listenerMap.containsKey(request.getRequestId())) {
                listenerMap.get(request.getRequestId()).onChannelError(
                        new RpcException("send message to remote server=" + socketAddress.toString() + " exception" , e));
            }
        }
    }

    public void removeListener(RpcRequest request) {
        listenerMap.remove(request.getRequestId());
    }

}





