package com.xiaogch.rpc.server;

import com.xiaogch.common.util.SystemUtil;
import com.xiaogch.rpc.codec.RpcCommonEncoder;
import com.xiaogch.rpc.codec.RpcRequestDecoder;
import com.xiaogch.rpc.meta.*;
import com.xiaogch.rpc.zk.ZkServiceRegister;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/16 14:56 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */

@Service
public abstract class AbstractRpcServer {

    static Logger LOGGER = LogManager.getLogger(AbstractRpcServer.class);

    private static final int DEFAULT_PORT = 10000;

    private static final int DEFAULT_MAX_FRAME_LENGTH = 1024 * 1024;

    @Autowired
    ZkServiceRegister zkServiceRegister;

    /**
     * 启动RPC服务
     */
    public void start() {
        startWithPort(getPort(DEFAULT_PORT));
    }

    /**
     * 启动rpc服务 , 指定端口
     * @param port
     */
    public void startWithPort(int port) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        bootstrap.group(boss, worker);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.SO_BACKLOG, 512);
        bootstrap.handler(new LoggingHandler(LogLevel.INFO));
        bootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                nioSocketChannel.pipeline()
//                                .addLast("sys_frameDecoder" , new LengthFieldBasedFrameDecoder(
//                                        DEFAULT_MAX_FRAME_LENGTH, 0 , 2 ,
//                                        0, 2))
                        .addLast("rpc_requestDecoder", new RpcRequestDecoder(DEFAULT_MAX_FRAME_LENGTH, 0, 2))
                        .addLast("rpc_commonEncoder", new RpcCommonEncoder())
                        .addLast("rpc_InboundHandler", new RpcServiceInboundHandler());
            }
        });

        try {
            ChannelFuture future = bootstrap.bind(port);
            future.sync();
            LOGGER.info("rpc service has started port={}" , port);

            // 服务注册到 zookeeper
            ServiceInfo serviceInfo = new ServiceInfo(getServiceEnum());
            HostAndPort hostAndPort = new HostAndPort(SystemUtil.getIpAddress(), port);
            serviceInfo.setHostAndPort(hostAndPort);
            serviceInfo.setPid(SystemUtil.getPid());
            serviceInfo.setStartupTime(new Date());
            serviceInfo.setServiceType(getServiceType());
            serviceInfo.setServiceEnv(getServiceEnv());
            serviceInfo.setServiceMode(getServiceMode());
            if (zkServiceRegister.registerServiceNode(serviceInfo)) {
                LOGGER.info("****************************************************************");
                LOGGER.info("***** register service {} to zookeeper success ****" , serviceInfo);
                LOGGER.info("****************************************************************");
            } else {
                LOGGER.warn("****************************************************************");
                LOGGER.warn("***** register service {} to zookeeper failure ****" , serviceInfo);
                LOGGER.warn("****************************************************************");
            }
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage() , e);
        }

    }


    /**
     * 获取端口
     * @param portBegin 起始端口
     * @return
     */
    private int getPort(int portBegin) {
        int port = portBegin;
        while (port < 65535) {
            try {
                ServerSocket socket = new ServerSocket(port);
                socket.close();
                return port;
            } catch (IOException e) {
                LOGGER.warn("port:{} has been bind" , port);
            }
            port ++;
        }
        throw new IllegalArgumentException("no usable port");
    }

    /**
     * 服务模式
     * @return
     */
    public abstract ServiceMode getServiceMode();

    /**
     * 服务类型
     * @return
     */
    public abstract ServiceType getServiceType();

    /**
     * 服务环境
     * @return
     */
    public abstract ServiceEnv getServiceEnv();

    /**
     * 服务信息
     * @return
     */
    public abstract ServiceEnum getServiceEnum();

}
