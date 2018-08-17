package com.xiaogch.common.netty.protocol.server;

import com.xiaogch.common.netty.protocol.codec.MessageDecoder;
import com.xiaogch.common.netty.protocol.codec.MessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/3/7 12:49 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class ProtocolServer {

    public void bind(int port) throws InterruptedException {
        //
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss , worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG , 100)
                .handler(new LoggingHandler(LogLevel.INFO))
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
                        ChannelPipeline channelPipeline = ch.pipeline();
                        channelPipeline.addLast("message decoder" , new MessageDecoder(1024*1024 , 4 , 4))
                                .addLast("message codec" , new MessageEncoder())
                                .addLast("login auth response handler" , new LoginAuthRespHandler())
                                .addLast("heart beat response handler" , new HeartBeatResHandler())
                                .addLast("read timeout hanlder" , new ReadTimeoutHandler(50));
                    }
                });
        bootstrap.bind(port).sync();
        //
    }

    public static void main(String...argvs) throws InterruptedException {

//        System.out.println(System.currentTimeMillis());
         new ProtocolServer().bind(10002);
    }
}
