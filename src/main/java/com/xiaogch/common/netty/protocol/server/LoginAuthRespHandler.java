package com.xiaogch.common.netty.protocol.server;

import com.xiaogch.common.netty.protocol.message.Header;
import com.xiaogch.common.netty.protocol.message.Message;
import com.xiaogch.common.netty.protocol.message.MessageType;
import io.netty.channel.*;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/2/28 13:16 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {

    private Map<String , Boolean> nodeCheck = new ConcurrentHashMap<>();

    private Map<String , Boolean> whiteMap;
    {
        whiteMap = new HashMap<>();
        whiteMap.put("127.0.0.1" , true);
        whiteMap.put("10.251.221.103" , true);
    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelRead(Object)} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message requestMsg = (Message) msg;
        if (requestMsg != null && requestMsg.getHeader() != null &&
                requestMsg.getHeader().getType() == MessageType.HANDSHAKE_REQ.value()) {

            InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
            String nodeFlag = inetSocketAddress.toString();
            Message responseMsg;
            if (nodeCheck.containsKey(nodeFlag) || whiteMap.containsKey(inetSocketAddress.getAddress().getHostAddress())) {
                responseMsg = buildMessage((byte)-1);
            } else {
                nodeCheck.put(nodeFlag , true);
                responseMsg = buildMessage((byte)0);
            }
            System.out.println("Login request is : " + requestMsg + " response is :" + responseMsg);
            ctx.writeAndFlush(msg);
        } else {
            ctx.fireChannelRead(msg);
        }
    }


    private Message buildMessage(Object body){
        Message message = new Message();
        Header header = new Header();
        header.setType(MessageType.HANDSHAKE_RES.value());
        message.setHeader(header);
        message.setBody(body);
        return message;
    }

    /**
     * Calls {@link ChannelHandlerContext#fireExceptionCaught(Throwable)} to forward
     * to the next {@link ChannelHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        nodeCheck.remove(inetSocketAddress.toString());
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }
}
