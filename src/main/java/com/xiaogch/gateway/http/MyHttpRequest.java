package com.xiaogch.gateway.http;

import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cookie.*;
import io.netty.handler.codec.http.cookie.Cookie;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

public class MyHttpRequest implements HttpRequest {

    HttpRequest httpRequest;

    Map<String , String> parameterMap = new LinkedHashMap<>();

    Cookie[] cookies;

    public MyHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
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

    public static void main(String[] args) {
        HttpServletRequest httpServletRequest;

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


    public Cookie[] getCookies() {
        if (cookies == null) {
            return new Cookie[0];
        }
        return cookies;
    }


}
