package com.xiaogch.rpc;

import io.netty.channel.Channel;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/22 11:33 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class RpcClientInvokeHelpers {

    /**
     * 通过oneWay 的方式调用
     * @param channel
     * @param rpcRequest
     * @throws RpcException
     * @throws InterruptedException
     */
    public static void syncInvokeByOneway(Channel channel , RpcRequest rpcRequest) throws RpcException, InterruptedException {
        final RpcResponse[] responseHolder = new RpcResponse[1];
        final RpcException[] exceptionHolder = new RpcException[1];
        final CountDownLatch latch = new CountDownLatch(1);
        responseHolder[0] = null;
        exceptionHolder[0] = null;
        channel.eventLoop().execute(()->
            channel.pipeline().get(RpcClientHandler.class).sendMessage(rpcRequest, new RpcSendMessageListener() {
                @Override
                public void onRequestSent() {
                    latch.countDown();
                }

                @Override
                public void onResponseReceived(RpcResponse response) {

                }

                @Override
                public void onChannelError(RpcException e) {
                    exceptionHolder[0] = e;
                    latch.countDown();
                }
            })
        );
        latch.await();

        channel.pipeline().get(RpcClientHandler.class).removeListener(rpcRequest);

        if (exceptionHolder[0] != null) {
            throw exceptionHolder[0];
        }
    }

    /**
     * 同步调用
     * @param channel
     * @param rpcRequest
     * @return
     * @throws RpcException
     * @throws InterruptedException
     */
    public static RpcResponse syncInvoke(Channel channel , RpcRequest rpcRequest) throws RpcException, InterruptedException {
        final RpcResponse[] responseHolder = new RpcResponse[1];
        final RpcException[] exceptionHolder = new RpcException[1];
        final CountDownLatch latch = new CountDownLatch(1);
        responseHolder[0] = null;
        exceptionHolder[0] = null;
        channel.eventLoop().execute(()->
                channel.pipeline().get(RpcClientHandler.class)
                        .sendMessage(rpcRequest, new RpcSendMessageListener() {
                @Override
                public void onRequestSent() {

                }

                @Override
                public void onResponseReceived(RpcResponse response) {
                    responseHolder[0] = response;
                    latch.countDown();
                }

                @Override
                public void onChannelError(RpcException e) {
                    exceptionHolder[0] = e;
                    latch.countDown();
                }
            })
        );

        latch.await();
        channel.pipeline().get(RpcClientHandler.class).removeListener(rpcRequest);

        if (exceptionHolder[0] != null) {
            throw exceptionHolder[0];
        } else {
            return responseHolder[0];
        }
    }


    /**
     * 异步调用
     * @param channel
     * @param rpcRequest
     * @return
     * @throws RpcException
     * @throws InterruptedException
     */
    public static Future<RpcResponse> asyncInvoke(Channel channel , RpcRequest rpcRequest) throws RpcException, InterruptedException {
       return channel.eventLoop().submit(()-> {
           CountDownLatch latch = new CountDownLatch(1);
           RpcResponse[] responseHolder = new RpcResponse[1];
           RpcException[] exceptionHolder = new RpcException[1];
           channel.pipeline().get(RpcClientHandler.class).sendMessage(rpcRequest, new RpcSendMessageListener() {
               @Override
               public void onRequestSent() {

               }

               @Override
               public void onResponseReceived(RpcResponse response) {
                   responseHolder[0] = response;
                   latch.countDown();
               }

               @Override
               public void onChannelError(RpcException e) {
                   exceptionHolder[0] = e;
                   latch.countDown();
               }
           });
           latch.await();
           channel.pipeline().get(RpcClientHandler.class).removeListener(rpcRequest);
           if (exceptionHolder[0] != null) {
               throw exceptionHolder[0];
           }
           return responseHolder[0];
        });
    }



}
