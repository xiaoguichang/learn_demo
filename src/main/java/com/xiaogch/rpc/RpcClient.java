package com.xiaogch.rpc;

import com.xiaogch.rpc.codec.RpcCommonEncoder;
import com.xiaogch.rpc.codec.RpcResponseDecoder;
import com.xiaogch.rpc.meta.HostAndPort;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.springframework.util.Assert;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

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
    private static final Map<Class , RpcInvocationHandler> rpcInvocationHandlerMap = new ConcurrentHashMap<>();
    private static ReentrantLock reentrantLock = new ReentrantLock();


    public static <T> T getInstance(HostAndPort hostAndPort , Class<T> clazz) {
        RpcInvocationHandler rpcInvocationHandler = rpcInvocationHandlerMap.get(clazz);
        if (rpcInvocationHandler != null) {
            try {
                return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                        new Class[]{clazz}, rpcInvocationHandler);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } else {
            reentrantLock.lock();
            try {
                rpcInvocationHandler = rpcInvocationHandlerMap.get(clazz);
                if (rpcInvocationHandler == null) {
                    rpcInvocationHandler = new RpcInvocationHandler(hostAndPort);
                    rpcInvocationHandlerMap.put(clazz , rpcInvocationHandler);
                }
                return (T) Proxy.newProxyInstance(clazz.getClassLoader() ,
                        new Class[]{clazz} , rpcInvocationHandler);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    //已连接主机的缓存
    private static Map<String, RpcClient> clientMap = new HashMap<>();

    private Channel channel;

    private EventLoopGroup group;

    private String ip;

    private int port;

    private RpcClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public static RpcClient getConnect(String host, int port) throws InterruptedException {
        if (clientMap.containsKey(host + port)) {
            return clientMap.get(host + port);
        }
        RpcClient con = connect(host, port);
        clientMap.put(host + port, con);
        return con;
    }

    private static RpcClient connect(String host, int port) throws InterruptedException {
        RpcClient client = new RpcClient(host, port);

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
                        pipeline.addLast("sys_frameDecoder" , new LengthFieldBasedFrameDecoder(
                                DEFAULT_MAX_FRAME_LENGTH, 0 , 2 ,
                                0, 2))
                                .addLast("rpc_responseDecoder" , new RpcResponseDecoder())
                                .addLast("rpc_commonEncoder" , new RpcCommonEncoder())
                                .addLast("rpc_clientHandler" , new RpcClientHandler());
                    }
                });

        ChannelFuture future = bootstrap.connect(host, port).sync();
        Channel c = future.channel();

        client.setChannel(c);
        client.setGroup(group);
        return client;
    }

    public Object invoke(RpcRequest request) throws Exception {
        RpcClientHandler rpcClientHandler = channel.pipeline().get(RpcClientHandler.class);
        Assert.notNull(rpcClientHandler , "rpcClientHandler can't be null");
        return rpcClientHandler.sendRequest(new RpcClientHandler.SendRequestCallable(request)).get();
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


}

