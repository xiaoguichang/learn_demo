package com.xiaogch.gateway;

import com.xiaogch.gateway.codec.GatewayHttpRequestDecoder;
import com.xiaogch.gateway.filter.GatewayFilterManager;
import com.xiaogch.gateway.filter.MyPreFilter2;
import com.xiaogch.gateway.filter.RequestIpPreFilter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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
@Component
public class GatewayServer {

    static Logger LOGGER = LogManager.getLogger(GatewayServer.class);


    private int port = 20000;

    private int maxLength = 5*1024; //5K

    @PostConstruct
    public void start() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        initFilter();

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
                                .addLast("app_MyHttpRequestDecoder" , new GatewayHttpRequestDecoder())
                                .addLast("sys_httpResponseEncoder" , new HttpResponseEncoder())
                                .addLast("app_httpServerInboundHandler" , new HttpServerInboundHandler());
                    }
                });

        try {
            ChannelFuture future = bootstrap.bind(port);
            future.sync();
            LOGGER.info("gateway server has started port={}" , port);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage() , e);
        }

    }


    private void initFilter(){
        GatewayFilterManager manager = GatewayFilterManager.getInstance();
        manager.regist(null , new RequestIpPreFilter());
        manager.regist(null , new MyPreFilter2());
    }

    public static void main(String[] args) {
        GatewayServer server = new GatewayServer();
        System.out.println("start server begin ...");
        LOGGER.info("start server begin ...");
        server.start();
        System.out.println("start server end ...");
        LOGGER.info("start server end ...");
    }
}
