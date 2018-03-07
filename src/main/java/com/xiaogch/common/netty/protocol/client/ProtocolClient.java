package com.xiaogch.common.netty.protocol.client;

import com.xiaogch.common.netty.protocol.codec.MessageDecoder;
import com.xiaogch.common.netty.protocol.codec.MessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/2/28 14:18 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class ProtocolClient {

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    EventLoopGroup group = new NioEventLoopGroup();

    public void connect(String host, int port) throws InterruptedException {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .option(ChannelOption.TCP_NODELAY , true)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
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
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("message decoder" , new MessageDecoder(1024 * 1024 , 4, 4))
                                    .addLast("message encoder" , new MessageEncoder())
                                    .addLast("login request handler" , new LoginReqHandler())
                                    .addLast("heart beat request handler" , new HeartBeatReqHandler())
                                    .addLast("read timeout handler" , new ReadTimeoutHandler(60));
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect(
                    new InetSocketAddress(host , port)).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 重连
            executorService.execute(()->{
                try {
                    TimeUnit.SECONDS.sleep(5);
                    connect(host, port);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.interrupted();
                }
            });
        }
    }

    public static void main(String...argvs) throws InterruptedException {
        new ProtocolClient().connect("127.0.0.1" , 10002);
    }

}
