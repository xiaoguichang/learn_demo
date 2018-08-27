package com.xiaogch.rpc.client;

import com.xiaogch.rpc.RpcException;
import com.xiaogch.rpc.RpcRequest;
import com.xiaogch.rpc.RpcResponse;
import com.xiaogch.rpc.codec.RpcCommonEncoder;
import com.xiaogch.rpc.codec.RpcResponseDecoder;
import com.xiaogch.rpc.meta.HostAndPort;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.HashMap;
import java.util.Map;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/16 18:46 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class RpcClient {
    private static final int DEFAULT_MAX_FRAME_LENGTH = 1024 * 1024;

    //已连接主机的缓存
    private static Map<String, RpcClient> clientMap = new HashMap<>();

    private Channel channel;

    private EventLoopGroup group;

    private HostAndPort hostAndPort;

    private static final Integer NO_WRITER_IDLE_TIMEOUT = 10000;
    private static final Integer NO_ALL_IDLE_TIMEOUT = 10000;

    private RpcClient(HostAndPort hostAndPort) {
        this.hostAndPort = hostAndPort;
    }

    /**
     * 获取rpc 客户端
     * @param hostAndPort
     * @return
     */
    public static RpcClient getClient(HostAndPort hostAndPort) {
        if (clientMap.containsKey(hostAndPort.toString())) {
            return clientMap.get(hostAndPort.toString());
        }

        RpcClient rpcClient = connect(hostAndPort);
        clientMap.put(hostAndPort.toString(), rpcClient);
        return rpcClient;
    }

    /**
     * 连接rpc 客户端
     * @param hostAndPort
     * @return
     */
    private static RpcClient connect(HostAndPort hostAndPort) {
        try {
            RpcClient client = new RpcClient(hostAndPort);

            /** 启动netty 客户端 */
            EventLoopGroup group = new NioEventLoopGroup();
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
                            pipeline.addLast("rpc_commonEncoder" , new RpcCommonEncoder())
                                    .addLast("rpc_responseDecoder" , new RpcResponseDecoder(DEFAULT_MAX_FRAME_LENGTH, 0 , 2))
                                    .addLast("rpc_clientHandler" , new RpcClientHandler());
//                                .addLast("idleTimeoutHandler", new IdleStateHandler(NO_WRITER_IDLE_TIMEOUT, NO_WRITER_IDLE_TIMEOUT, NO_ALL_IDLE_TIMEOUT, TimeUnit.MILLISECONDS));
                        }
                    });
            ChannelFuture future = bootstrap.connect(hostAndPort.getHost(), hostAndPort.getPort())
                    .addListener(new GenericFutureListener(){
                        @Override
                        public void operationComplete(Future future) throws Exception {
                            future.get();
                        }
                    })
                    .sync();
            Channel c = future.channel();
            client.setChannel(c);
            client.setGroup(group);
            return client;
        } catch (Exception e) {
            throw new RpcException("connect to " + hostAndPort.toString() + " exception" , e);
        }
    }

    /***
     * rpc 方法调用
     * @param request
     * @return
     * @throws Exception
     */
    public Object syncInvoke(RpcRequest request) throws Exception {
        return RpcClientInvokeHelpers.syncInvoke(channel , request).getData();
    }

    /***
     * rpc 方法调用
     * @param request
     * @return
     * @throws Exception
     */
    public java.util.concurrent.Future<RpcResponse> asyncInvoke(RpcRequest request) throws Exception {
        return RpcClientInvokeHelpers.asyncInvoke(channel , request);
    }

    /***
     * rpc 方法调用
     * @param request
     * @return
     * @throws Exception
     */
    public void invokeOnway(RpcRequest request) throws Exception {
        RpcClientInvokeHelpers.syncInvokeByOneway(channel , request);
    }




    public void closeConnect() {
        this.group.shutdownGracefully();
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void setGroup(EventLoopGroup group) {
        this.group = group;
    }
}

