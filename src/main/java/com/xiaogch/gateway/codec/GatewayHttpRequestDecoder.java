package com.xiaogch.gateway.codec;

import com.xiaogch.gateway.http.GatewayHttpRequest;
import com.xiaogch.gateway.http.GatewayHttpSession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.*;

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
public class GatewayHttpRequestDecoder extends MessageToMessageDecoder<FullHttpRequest>{

    static Logger LOGGER = LogManager.getLogger(GatewayHttpRequestDecoder.class);
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

        Map<String , String> parameterMap = paramterParase(msg);
        // cookie ...
        Map<String , Cookie> cookieMap = parseCookie(msg);
        // httpSession ...
        GatewayHttpSession httpSession = parseSession(cookieMap);
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();

        LOGGER.info("remoteAddress : {}", address);
        GatewayHttpRequest gatewayHttpRequest = new GatewayHttpRequest(msg, parameterMap ,
                cookieMap , httpSession , address.getHostName());
        out.add(gatewayHttpRequest);
    }

    private GatewayHttpSession parseSession(Map<String, Cookie> cookieMap) {
        //Cookie:JSESSIONID=E094E950654C8C3564C6306EB8D70F37
       Cookie cookie = cookieMap.get("JSESSIONID");
       if (cookie == null) {
           String sessionId = UUID.randomUUID().toString();
           return new GatewayHttpSession(sessionId);
       } else {
           GatewayHttpSession session = new GatewayHttpSession();
           session.setSessionId(cookie.value());
           return session;
       }

    }

    public Map<String , Cookie> parseCookie(HttpRequest httpRequest) {

        Map<String , Cookie> cookieMap = new HashMap<>();
        String headCookie = httpRequest.headers().get("Cookie");
        if (StringUtils.hasText(headCookie)) {
            Set<Cookie> cookies = ServerCookieDecoder.STRICT.decode(headCookie);
            Iterator<Cookie> iterator = cookies.iterator();
            while (iterator.hasNext()) {
                Cookie cookie = iterator.next();
                cookieMap.put(cookie.name() , cookie);
            }
        }
        return cookieMap;
    }


    public Map<String,String> paramterParase(FullHttpRequest msg){
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
        return parameterMap;
    }
}
