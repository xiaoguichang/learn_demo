package com.xiaogch.gateway;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cookie.*;
import io.netty.handler.codec.http.cookie.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.Set;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/4 16:20 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class HttpServerInboundHandler extends ChannelInboundHandlerAdapter  {

    static Logger logger = LoggerFactory.getLogger(HttpServerInboundHandler.class);

    public HttpServerInboundHandler() {
        super();
    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelRegistered()} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("httpServerInboundHandler channelRegistered ...");
//        System.out.println("httpServerInboundHandler channelRegistered ...");
        super.channelRegistered(ctx);
    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelUnregistered()} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("httpServerInboundHandler channelUnregistered ...");
        super.channelUnregistered(ctx);
    }

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
        System.out.println("httpServerInboundHandler channelActive ...");
        super.channelActive(ctx);
    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelInactive()} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("httpServerInboundHandler channelInactive ...");

        super.channelInactive(ctx);
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
        System.out.println("httpServerInboundHandler channelRead ...");

        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;

        }
        System.out.println(msg.getClass());

//        HttpObjectAggregator.AggregatedFullHttpRequest request;

        System.out.println(JSONObject.toJSONString(msg));
        super.channelRead(ctx, msg);
    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelReadComplete()} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("httpServerInboundHandler channelReadComplete ...");
        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1 , HttpResponseStatus.OK);
        fullHttpResponse.content().writeBytes("ok has got the msg".getBytes());
        ctx.writeAndFlush(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * Calls {@link ChannelHandlerContext#fireUserEventTriggered(Object)} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     * @param evt
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("httpServerInboundHandler userEventTriggered ...");

        super.userEventTriggered(ctx, evt);
    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelWritabilityChanged()} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     */
    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println("httpServerInboundHandler channelWritabilityChanged ...");
        super.channelWritabilityChanged(ctx);
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
        System.out.println("httpServerInboundHandler exceptionCaught ...");

//        CookieEncoder cookieEncoder;
        super.exceptionCaught(ctx, cause);
    }

}
