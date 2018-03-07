package com.xiaogch.common.netty.protocol.codec;

import com.xiaogch.common.netty.protocol.message.Header;
import com.xiaogch.common.netty.protocol.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import org.jboss.marshalling.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/2/28 10:18 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class MessageDecoder extends LengthFieldBasedFrameDecoder {

    private final Unmarshaller unmarshaller;

    public MessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) throws IOException {

        super(maxFrameLength , lengthFieldOffset , lengthFieldLength);
        MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration config = new MarshallingConfiguration();
        config.setVersion(5);
        unmarshaller = factory.createUnmarshaller(config);
    }

    /**
     * Create a frame out of the {@link ByteBuf} and return it.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link ByteToMessageDecoder} belongs to
     * @param in  the {@link ByteBuf} from which to read data
     *
     * @return frame           the {@link ByteBuf} which represent the frame or {@code null} if no frame could
     * be created.
     */
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame != null) {
            Message message = new Message();
            Header header = new Header();
            header.setCrcCode(frame.readInt());
            header.setLength(frame.readInt());
            header.setSessionId(frame.readLong());
            header.setType(frame.readByte());
            header.setPriority(frame.readByte());

            int attachmentSize = frame.readInt();
            Map<String , Object> attachment;
            if (attachmentSize > 0) {
                attachment = new HashMap<>(attachmentSize);
                for (int index = 0 ; index < attachmentSize ; index ++) {
                    int keySize = frame.readInt();
                    byte[] keyArray = new byte[keySize];
                    frame.readBytes(keyArray);
                    String key = new String(keyArray , "utf-8");
                    attachment.put(key , decode(in));
                }
            } else {
                attachment = new HashMap<>();
            }
            header.setAttachment(attachment);
            message.setHeader(header);
            if (in.readableBytes() > 4) {
                message.setBody(decode(in));
            }
            return message;
        }
        return null;
    }

    public Object decode(ByteBuf in) {
        int objectSize = in.readInt();
        ByteBuf buf = in.slice(in.readerIndex() , objectSize);
        ChannelBufferByteInput input = new ChannelBufferByteInput(buf);
        try {
            unmarshaller.start(input);
            Object object = unmarshaller.readObject();
            unmarshaller.finish();
            in.readerIndex(in.readerIndex() + objectSize);
            return object;
        } catch(Exception e){
            throw new RuntimeException(e);
        } finally{
            try {
                unmarshaller.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
