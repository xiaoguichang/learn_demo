/**
 * ProjectName: jobrecommend-service <BR>
 * File name: JssdkJsApiServiceImpl.java <BR>
 * Author: guich  <BR>
 * Project: jobrecommend-service <BR>
 * Version: v 1.0 <BR>
 * Date: 2017/10/24 17:16 <BR>
 * Description: 微信JSSDK服务实现 <BR>
 * Function List: 1、JSSDK签名 <BR>
 */
package com.xiaogch.wechat.common.service.impl;

import com.xiaogch.common.util.security.MessageDigestUtil;
import com.xiaogch.wechat.common.service.JssdkJsApiService;
import com.xiaogch.wechat.common.service.TokenAndTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service("jssdkJsApiService")
public class JssdkJsApiServiceImpl implements JssdkJsApiService {

    @Autowired
    private TokenAndTicketService tokenAndTicketService;

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
    @Override
    public Map<String, String> sign(String url , String appId , String secret) throws Exception {
        Map<String , String> returnMap = new HashMap<>();
        String jsapiTicket = tokenAndTicketService.getJsapiTicket(appId , secret);
        String nonce_str = genNonceStr();
        String timestamp = genTimestamp();
        //注意这里参数名必须全部小写，且必须有序
        String temp = "jsapi_ticket=" + jsapiTicket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        String signature = MessageDigestUtil.sha1(temp , "utf-8");

        returnMap.put("url", url);
        returnMap.put("jsapi_ticket", jsapiTicket);
        returnMap.put("nonceStr", nonce_str);
        returnMap.put("timestamp", timestamp);
        returnMap.put("signature", signature);
        return returnMap;
    }

    private String genNonceStr() {
        return UUID.randomUUID().toString();
    }

    private String genTimestamp() {
        return Long.toString(System.currentTimeMillis());
    }

}
