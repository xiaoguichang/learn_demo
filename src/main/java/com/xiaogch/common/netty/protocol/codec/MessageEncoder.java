package com.xiaogch.common.netty.protocol.codec;

import com.xiaogch.common.netty.protocol.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/2/27 14:25 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public final class MessageEncoder extends MessageToByteEncoder<Message> {

    private static byte[] LENGTH_PLACEHOLDER = new byte[4];

    private final Marshaller marshaller;

    public MessageEncoder() throws IOException {
        MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration config = new MarshallingConfiguration();
        config.setVersion(5);
        marshaller = factory.createMarshaller(config);
    }

    /**
     * Encode a message into a {@link ByteBuf}. This method will be called for each written message that can be handled
     * by this codec.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link MessageToByteEncoder} belongs to
     * @param msg the message to encode
     * @param out the {@link ByteBuf} into which the encoded message will be written
     *
     * @throws Exception is thrown if an error occurs
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {

        out.writeInt(msg.getHeader().getCrcCode());
        out.writeInt(msg.getHeader().getLength());
        out.writeLong(msg.getHeader().getSessionId());
        out.writeByte(msg.getHeader().getType());
        out.writeByte(msg.getHeader().getPriority());
        out.writeInt(msg.getHeader().getAttachment().size());

        msg.getHeader().getAttachment().forEach((key , value)-> {
            try {
                byte[] keyArray = key.getBytes("utf-8");
                out.writeInt(keyArray.length);
                out.writeBytes(keyArray);
                encode(value , out);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });

        if (msg.getBody() != null) {
            encode(msg.getBody() , out);
        } else {
            out.writeInt(0);
        }

        out.setInt(4 , out.readableBytes() - 8);
        System.out.println("encode success ...");
    }

    public void encode(Object value , ByteBuf out) {
        try {
            int lengthPos = out.writerIndex();
            out.writeBytes(LENGTH_PLACEHOLDER);
            ChannelBufferByteOutput output = new ChannelBufferByteOutput(out);
            marshaller.start(output);
            marshaller.writeObject(value);
            marshaller.finish();
            out.setInt(lengthPos, out.writerIndex() - lengthPos - 4);
        } catch(IOException e){
            e.printStackTrace();
        } finally{
            try {
                marshaller.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
