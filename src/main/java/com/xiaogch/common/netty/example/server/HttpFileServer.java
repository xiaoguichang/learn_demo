package com.xiaogch.common.netty.example.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.net.ServerSocket;

public class HttpFileServer {

    static Logger logger = LogManager.getLogger(HttpFileServer.class);

    private static final String DEFAULT_URL = "/";

    public void run(int port) {
        EventLoopGroup boos = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boos , worker)
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
                            ch.pipeline().addLast("http-request-decoder" , new HttpRequestDecoder());
                            //
                            ch.pipeline().addLast("http-object-aggregator" , new HttpObjectAggregator(65535));

                            ch.pipeline().addLast("http-response-encode" , new HttpResponseEncoder());
                            //
                            ch.pipeline().addLast("chunk-write-handler" , new ChunkedWriteHandler());
                            ch.pipeline().addLast("http-file-server-handler" , new HttpStaticFileServerHandler());
                        }
                    });

            int finalPort = getPort(port);

            ChannelFuture future = serverBootstrap.bind(finalPort).sync();
            logger.info("file server has started , url is http://{}:{}{}" , "127.0.0.1" , finalPort , DEFAULT_URL);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error(e.getMessage() , e);
        } finally {
            boos.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    private int getPort(int port) {
        int tempPort = port;
        while(tempPort < 65535) {
            try {
                ServerSocket serverSocket = new ServerSocket(tempPort);
                serverSocket.close();
                return tempPort;
            } catch (Exception e) {

            }
            port ++;
        }
        throw new RuntimeException("can't find unbind port");
    }

    public static void main(String...argvs) {
        HttpFileServer httpFileServer = new HttpFileServer();
        httpFileServer.run(8080);
    }

}
