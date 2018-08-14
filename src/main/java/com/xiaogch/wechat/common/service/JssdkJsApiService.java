/**
 * ProjectName: jobrecommend-service <BR>
 * File name: JssdkJsApiService.java <BR>
 * Author: guich  <BR>
 * Project: jobrecommend-service <BR>
 * Version: v 1.0 <BR>
 * Date: 2017/10/24 17:16 <BR>
 * Description: 微信JSSDK服务 <BR>
 * Function List: 1、JSSDK签名 <BR>
 */
package com.xiaogch.wechat.common.service;

import java.util.Map;

public interface JssdkJsApiService {

    /***
     * 功能描述：JSSDK签名实现
     *
     * @param url 要签名的url
     * @param appId 微信公众号、微信小程序appId
     * @param secret 微信公众号、微信小程序secret
     * @return 具体的签名信息
     *
     * @throws Exception
     *
     * @author guich
     * @since 2017/10/24
     * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    Map<String, String> sign(String url, String appId, String secret) throws Exception;
}
