package com.xiaogch.common.http.util;

import com.alibaba.fastjson.JSONObject;
import com.xiaogch.common.http.exception.HttpRequestException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author: guich <BR>
 * Version: v 1.0 <BR>
 * Date: 2017/9/26 17:19 <BR>
 * Description: <BR>
 * Function List: <BR>
 */
public class HttpRequestUtil {

    /** http 请求 connectTimeout 默认1分钟 */
    private final static int CONNECT_TIMEOUT = 60*1000;

    /** http 请求 soketTimeout  默认1分钟 */
    private final static int SOCKET_TIMEOUT = 60*1000;


    /**
     * 数据格式为JSON发送post请求，若请求成功返回响应信息，否则抛出HttpRequestException异常
     * @param url 请求地址
     * @param parameter 请求参数
     * @return http response 返回的数据
     * @throws HttpRequestException 请求异常
     */
    public static JSONObject postRequestByJson(String url , JSONObject parameter) throws HttpRequestException {
        return postRequestByJson(url , parameter , CONNECT_TIMEOUT , SOCKET_TIMEOUT);
    }

    /**
     * 数据格式为JSON发送post请求，若请求成功返回响应信息，否则抛出HttpRequestException异常
     * @param url 请求地址
     * @param parameter 请求参数
     * @return http response 返回的数据
     * @throws HttpRequestException 请求异常
     */
    public static JSONObject postRequestByJson(String url , Map<String , String> parameter) throws HttpRequestException {
        return postRequestByJson(url , parameter , CONNECT_TIMEOUT , SOCKET_TIMEOUT);
    }


    /**
     * 数据格式为JSON发送post请求，若请求成功返回响应信息，否则抛出HttpRequestException异常
     * @param url 请求地址
     * @param parameter 请求参数
     * @param connectTimeout 连接超时
     * @param socketTimeOut 读取超时
     * @return http response 返回的数据
     * @throws HttpRequestException 请求异常
     */
    public static JSONObject postRequestByJson(String url , JSONObject parameter, int connectTimeout , int socketTimeOut) throws HttpRequestException {
        String result = doPostAndReturnString(url , parameter == null ? null : parameter.toJSONString() , connectTimeout , socketTimeOut);
        try {
            return JSONObject.parseObject(result);
        } catch (Exception e) {
            throw new HttpRequestException(e.getMessage() , e);
        }
    }

    /**
     * 数据格式为JSON发送post请求，若请求成功返回响应信息，否则抛出HttpRequestException异常
     * @param url 请求地址
     * @param parameter 请求参数
     * @param connectTimeout 连接超时
     * @param socketTimeOut 读取超时
     * @return http response 返回的数据
     * @throws HttpRequestException 请求异常
     */
    public static JSONObject postRequestByJson(String url , Map<String , String> parameter, int connectTimeout , int socketTimeOut) throws HttpRequestException {
        String result = doPostAndReturnString(url , parameter , connectTimeout , socketTimeOut);
        try {
            return JSONObject.parseObject(result);
        } catch (Exception e) {
            throw new HttpRequestException(e.getMessage() , e);
        }
    }

    /**
     * 数据格式为JSON发送get请求，若请求成功返回响应信息，否则抛出HttpRequestException异常
     * @param url 请求地址
     * @return http response 返回的数据
     * @throws HttpRequestException 请求异常
     */
    public static JSONObject getRequestByJson(String url) throws HttpRequestException {
        return getRequestByJson(url , CONNECT_TIMEOUT , SOCKET_TIMEOUT);
    }

    /**
     * 数据格式为JSON发送get请求，若请求成功返回响应信息，否则抛出HttpRequestException异常
     * @param url 请求地址
     * @param connectTimeout 连接超时
     * @param socketTimeOut 读取超时
     * @return http response 返回的数据
     * @throws HttpRequestException 请求异常
     */
    public static JSONObject getRequestByJson(String url , int connectTimeout , int socketTimeOut) throws HttpRequestException {
        String result = doGetAndReturnString(url , connectTimeout , socketTimeOut);
        try {
            return JSONObject.parseObject(result);
        } catch (Exception e) {
            throw new HttpRequestException(e.getMessage() , e);
        }
    }

    /**
     * 发送post请求，若请求成功返回响应信息，否则抛出HttpRequestException异常，键值对的方式
     * @param url 请求地址
     * @param requestParameter 请求参数
     * @return http response 返回的数据
     * @throws HttpRequestException 请求异常
     */
    public static String doPostAndReturnString(String url , String requestParameter) throws HttpRequestException {
        return doPostAndReturnString(url , requestParameter , CONNECT_TIMEOUT , SOCKET_TIMEOUT);
    }

    /**
     * 发送post请求，若请求成功返回响应信息，否则抛出HttpRequestException异常，键值对的方式
     * @param url 请求地址
     * @param requestParameter 请求参数
     * @param connectTimeout 连接超时
     * @param socketTimeOut 读取超时
     * @return http response 返回的数据
     * @throws HttpRequestException 请求异常
     */
    public static String doPostAndReturnString(String url , String requestParameter , int connectTimeout , int socketTimeOut) throws HttpRequestException {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig config = getRequestConfig(connectTimeout , socketTimeOut);
        httpPost.setConfig(config);
        httpPost.setEntity(new StringEntity(requestParameter , "utf-8"));
        return sendReqeust(httpPost);
    }

    /**
     * 发送post请求，若请求成功返回响应信息，否则抛出HttpRequestException异常，键值对的方式
     * @param url 请求地址
     * @param requestParameter 请求参数
     * @return http response 返回的数据
     * @throws HttpRequestException 请求异常
     */
    public static String doPostAndReturnString(String url , Map<String , String> requestParameter) throws HttpRequestException {
        return doPostAndReturnString(url , requestParameter , CONNECT_TIMEOUT , SOCKET_TIMEOUT);
    }

    /**
     * 发送post请求，若请求成功返回响应信息，否则抛出HttpRequestException异常，键值对的方式
     * @param url 请求地址
     * @param requestParameter 请求参数
     * @param connectTimeout 连接超时
     * @param socketTimeOut 读取超时
     * @return http response 返回的数据
     * @throws HttpRequestException 请求异常
     */
    public static String doPostAndReturnString(String url , Map<String , String> requestParameter , int connectTimeout , int socketTimeOut) throws HttpRequestException {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig config = getRequestConfig(connectTimeout , socketTimeOut);
        httpPost.setConfig(config);
        List<NameValuePair> nameValuePairs = getNameValuePairs(requestParameter);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs , "utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new HttpRequestException(e.getMessage() , e);
        }
        return sendReqeust(httpPost);
    }

    /**
     * 发送get请求，若请求成功返回响应信息，否则抛出HttpRequestException异常
     * @param url 请求地址
     * @return http response 返回的数据
     * @throws HttpRequestException 请求异常
     */
    public static String doGetAndReturnString(String url) throws HttpRequestException {
        return doGetAndReturnString(url , CONNECT_TIMEOUT , SOCKET_TIMEOUT);
    }
    /**
     * 发送get请求，若请求成功返回响应信息，否则抛出HttpRequestException异常
     * @param url 请求地址
     * @param connectTimeout 连接超时
     * @param socketTimeOut 读取超时
     * @return http response 返回的数据
     * @throws HttpRequestException 请求异常
     */
    public static String doGetAndReturnString(String url , int connectTimeout , int socketTimeOut) throws HttpRequestException {
        HttpGet httpGet = new HttpGet(url);
        RequestConfig config = getRequestConfig(connectTimeout , socketTimeOut);
        httpGet.setConfig(config);
        return sendReqeust(httpGet);
    }

    private static String sendReqeust(HttpUriRequest httpUriRequest) throws HttpRequestException {
        HttpClient client = HttpClients.createDefault();
        HttpResponse response;
        try {
            response = client.execute(httpUriRequest);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                return EntityUtils.toString(response.getEntity());
            } else {
                StringBuilder sb = new StringBuilder(response.getStatusLine().toString());
                sb.append(EntityUtils.toString(response.getEntity()));
                throw new HttpRequestException(sb.toString());
            }
        } catch (ClientProtocolException e) {
            throw new HttpRequestException(e.getMessage() , e);
        } catch (IOException e) {
            throw new HttpRequestException(e.getMessage() , e);
        }
    }

    /**
     * 将Map类型的请求参数转换成NameValuePair
     * @param requestParameter
     * @return
     */
    private static List<NameValuePair> getNameValuePairs(Map<String , String> requestParameter) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        if (requestParameter != null && !requestParameter.isEmpty()) {
            Set<String> keys = requestParameter.keySet();
            for (String key : keys) {
                nameValuePairs.add(new BasicNameValuePair(key, requestParameter.get(key)));
            }
        }
        return nameValuePairs;
    }

    /**
     * 生成 RequestConfig 配置信息
     * @param connectTimeout 连接超时时间
     * @param socketTimeout 读取超时时间
     * @return
     */
    private static RequestConfig getRequestConfig(int connectTimeout , int socketTimeout) {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .build();
        return config;
    }

}
