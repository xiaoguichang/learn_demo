package com.xiaogch.rpc.server;

import com.xiaogch.rpc.RpcRequest;
import com.xiaogch.rpc.RpcResponse;
import com.xiaogch.rpc.meta.RpcMethodHandler;
import com.xiaogch.rpc.meta.RpcServiceMeta;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Author: guich <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/15 19:21 <BR>
 * Description: Rpc 服务处理类 <BR>
 * Function List:  <BR>
 */
public class RpcServiceInboundHandler extends ChannelInboundHandlerAdapter {

    static Logger LOGGER = LogManager.getLogger(RpcServiceInboundHandler.class);

    public RpcServiceInboundHandler() {
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
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg != null && msg instanceof RpcRequest) {
            RpcRequest rpcRequest = (RpcRequest) msg;
            RpcResponse rpcResponse = new RpcResponse();
            try {
                RpcServiceMeta rpcServiceMeta = RpcServiceRegistry.getRpcServiceMeta(rpcRequest.getServiceClassName());
                RpcMethodHandler rpcMethodHandler = rpcServiceMeta.getRpcMethodHandler(rpcRequest.getMethodName() , rpcRequest.getParameterTypes());
                Object object = rpcMethodHandler.invoke(rpcRequest.getParameters());
                LOGGER.info("rpc deal with {} , result is {}" , rpcRequest , object);
                rpcResponse.setCode(10000);
                rpcResponse.setMsg("success");
                rpcResponse.setData(object);
                rpcResponse.setRequestId(rpcRequest.getRequestId());
            } catch (Exception e) {
                rpcResponse.setCode(99999);
                rpcResponse.setMsg(e.getMessage());
                rpcResponse.setData(new Object());
                rpcResponse.setRequestId(rpcRequest.getRequestId());
            }
            ctx.writeAndFlush(rpcResponse);
        } else {
            super.channelRead(ctx, msg);
        }
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
}
