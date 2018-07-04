package com.xiaogch.gateway.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/4 17:34 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class HttpServeletRequestDecoder extends MessageToMessageDecoder<FullHttpRequest>{

    /**
     * Decode from one message to an other. This method will be called for each written message that can be handled
     * by this encoder.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link MessageToMessageDecoder} belongs to
     * @param msg the message to decode to an other one
     * @param out the {@link List} to which decoded messages should be added
     *
     * @throws Exception is thrown if an error occurs
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, FullHttpRequest msg, List<Object> out) throws Exception {
        HttpServletRequest a;
    }


    public void paramterParase(FullHttpRequest msg){
        HttpMethod httpMethod = msg.method();
        Map<String , String> parameterMap = new LinkedHashMap<>();

        // uri 参数解析
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(msg.uri());
        Map<String, List<String>> uriParameters = queryStringDecoder.parameters();
        uriParameters.forEach((key , value)-> parameterMap.put(key , value.get(0)));

        // post 参数解析
        if (HttpMethod.POST.equals(httpMethod)) {
            HttpPostRequestDecoder postRequestDecoder = new HttpPostRequestDecoder(msg);
            List<InterfaceHttpData> interfaceHttpDataList = postRequestDecoder.getBodyHttpDatas();
            interfaceHttpDataList.forEach(interfaceHttpData -> {

                switch (interfaceHttpData.getHttpDataType()) {
                    case Attribute:
                        Attribute attribute = (Attribute) interfaceHttpData;
                        try {
                            parameterMap.put(attribute.getName() , attribute.getValue());
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                        break;
                    case FileUpload:
                        break;
                    case InternalAttribute:
                        break;
                    default:
                        break;
                }
            });
        }

    }
}
