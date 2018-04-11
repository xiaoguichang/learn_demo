package com.xiaogch.common.netty.example.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/2/12 17:20 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class DiscardServer {

    /** 应用端口 */
    private short port;

    public DiscardServer(short port) {
        this.port = port;
    }

    public void run() {

        // boss 线程 , 接收请求
        EventLoopGroup boosGroup = new NioEventLoopGroup();

        // worker 线程, 处理由boss 线程接收并分发到worker的连接请求
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boosGroup , workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        /**
                         * This method will be called once the {@link Channel} was registered. After the method returns this instance
                         * will be removed from the {@link ChannelPipeline} of the {@link Channel}.
                         *
                         * @param ch the {@link Channel} which was registered.
                         *
                         * @throws Exception is thrown if an error occurs. In that case it will be handled by
                         *                   {@link #exceptionCaught(ChannelHandlerContext, Throwable)} which will by default close
                         *                   the {@link Channel}.
                         */
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ByteBuf delimiter = Unpooled.copiedBuffer("@_@".getBytes());
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(120 , delimiter));
//                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG , 128)
                    .childOption(ChannelOption.SO_KEEPALIVE , true);
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
