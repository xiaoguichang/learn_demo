package com.xiaogch.gateway;

import com.xiaogch.gateway.codec.MyHttpRequestDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/4 16:03 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class GatewayServer {

    static Logger logger = LoggerFactory.getLogger(GatewayServer.class);


    private int port = 20000;

    private int maxLength = 5*1024; //5K

    public void start() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        bootstrap.group(boss , worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG , 512)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<NioSocketChannel>(){
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline()
                                .addLast("sys_httpRequestDecoder" , new HttpRequestDecoder())
                                .addLast("sys_httpObjectAggregator" ,new HttpObjectAggregator(maxLength * 1024))
                                .addLast("app_MyHttpRequestDecoder" , new MyHttpRequestDecoder())
                                .addLast("sys_httpResponseEncoder" , new HttpRequestEncoder())
                                .addLast("app_httpServerInboundHandler" , new HttpServerInboundHandler());
                    }
                });

        try {
            ChannelFuture future = bootstrap.bind(port);
            future.sync();
            System.out.println("gateway server has started port=" + port);
            logger.info("gateway server has started port={}" , port);
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error(e.getMessage() , e);
        }

    }


    public static void main(String[] args) {
        GatewayServer server = new GatewayServer();
        System.out.println("start server begin ...");
        logger.info("start server begin ...");
        server.start();
        System.out.println("start server end ...");
        logger.info("start server end ...");
    }
}
