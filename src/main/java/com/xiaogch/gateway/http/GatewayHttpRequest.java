package com.xiaogch.gateway.http;

import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cookie.Cookie;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GatewayHttpRequest implements HttpRequest {

    HttpRequest httpRequest;

    Map<String , String> parameterMap = new ConcurrentHashMap<>();

    Map<String, Cookie> cookieMap = new ConcurrentHashMap<>();

    GatewayHttpSession httpSession;

    private String remoteAddr;

    public GatewayHttpRequest(HttpRequest httpRequest, Map<String, String> parameterMap,
                              Map<String, Cookie> cookieMap, GatewayHttpSession httpSession,
                              String remoteAddr){
        this.httpRequest = httpRequest;
        this.parameterMap.putAll(parameterMap);
        this.cookieMap.putAll(cookieMap);
        this.httpSession = httpSession;
        this.remoteAddr = remoteAddr;
    }


    /**
     * @deprecated Use {@link #method()} instead.
     */
    @Override
    public HttpMethod getMethod() {
        return method();
    }

    /**
     * Returns the {@link HttpMethod} of this {@link HttpRequest}.
     *
     * @return The {@link HttpMethod} of this {@link HttpRequest}
     */
    @Override
    public HttpMethod method() {
        return httpRequest.method();
    }

    /**
     * Set the {@link HttpMethod} of this {@link HttpRequest}.
     *
     * @param method
     */
    @Override
    public HttpRequest setMethod(HttpMethod method) {
        return httpRequest.setMethod(method);
    }

    /**
     * @deprecated Use {@link #uri()} instead.
     */
    @Override
    public String getUri() {
        return uri();
    }

    /**
     * Returns the requested URI (or alternatively, path)
     *
     * @return The URI being requested
     */
    @Override
    public String uri() {
        return httpRequest.uri();
    }

    /**
     * Set the requested URI (or alternatively, path)
     *
     * @param uri
     */
    @Override
    public HttpRequest setUri(String uri) {
        return httpRequest.setUri(uri);
    }

    @Override
    public HttpRequest setProtocolVersion(HttpVersion version) {
        return httpRequest.setProtocolVersion(version);
    }

    /**
     * @deprecated Use {@link #protocolVersion()} instead.
     */
    @Override
    public HttpVersion getProtocolVersion() {
        return protocolVersion();
    }

    /**
     * Returns the protocol version of this {@link HttpMessage}
     */
    @Override
    public HttpVersion protocolVersion() {
        return httpRequest.protocolVersion();
    }

    /**
     * Returns the headers of this message.
     */
    @Override
    public HttpHeaders headers() {
        return httpRequest.headers();
    }

    public String getHeader(String name) {
        return headers().get(name);
    }

    /**
     * @deprecated Use {@link #decoderResult()} instead.
     */
    @Override
    public DecoderResult getDecoderResult() {
        return decoderResult();
    }

    /**
     * Returns the result of decoding this object.
     */
    @Override
    public DecoderResult decoderResult() {
        return httpRequest.decoderResult();
    }

    /**
     * Updates the result of decoding this object. This method is supposed to be invoked by a decoder.
     * Do not call this method unless you know what you are doing.
     *
     * @param result
     */
    @Override
    public void setDecoderResult(DecoderResult result) {
        httpRequest.setDecoderResult(result);
    }

    /**
     * 获取参数值
     * @param name
     * @return
     */
    public String getParameter(String name) {
        return parameterMap.get(name);
    }

    public Map<String, String> getParamters() {
        return parameterMap;
    }


    public Cookie getCookie(String name) {
        return cookieMap.get(name);
    }

    public GatewayHttpSession getSession() {
        return httpSession;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }
}
