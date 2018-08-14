/**
 * ProjectName: jobrecommend-service <BR>
 * File name: TokenAndTicketService.java <BR>
 * Author: guich  <BR>
 * Project: jobrecommend-service <BR>
 * Version: v 1.0 <BR>
 * Date: 2017/10/24 17:16 <BR>
 * Description: 微信accesstoken、ticket获取服务 <BR>
 * Function List: 1、获取微信accesstoken<BR>
 *                2、获取微信WxCardTicket<BR>
 *                3、获取微信JsapiTicket<BR>
 */
package com.xiaogch.wechat.common.service;


public interface TokenAndTicketService {


    /***
     * 功能描述：获取微信JsapiTicket
     *
     * @param appId 微信公众号、微信小程序appId
     * @param secret 微信公众号、微信小程序secret
     *
     * @return 成功返回JsapiTicket
     *
     * @author guich
     * @since 2017/10/24
     * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    String getJsapiTicket(String appId, String secret);

    /***
     * 功能描述：获取微信WxCardTicket，用于微信卡包
     *
     * @param appId 微信公众号、微信小程序appId
     * @param secret 微信公众号、微信小程序secret
     *
     * @return 成功返回WxCardTicket
     *
     * @author guich
     * @since 2017/10/24
     * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    String getWxCardTicket(String appId, String secret);

    /***
     * 功能描述：获取微信accessToken
     *
     * @param appId 微信公众号、微信小程序appId
     *
     * @param secret 微信公众号、微信小程序secret
     *
     * @return 成功返回accessToken
     *
     * @author guich
     * @since 2017/10/24
     * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    String getAccessToken(String appId, String secret);


    /**
     * 功能描述：清理微信accessToken
     * @param appId 微信公众号、微信小程序appId
     * @return
     */
    boolean clearAccessToken(String appId);

    /**
     * 功能描述：清理微信JsapiTicket
     * @param appId 微信公众号、微信小程序appId
     * @return
     */
    boolean clearJsapiTicket(String appId);

    /**
     * 功能描述：清理微信WxCardTicket，用于微信卡包
     * @param appId 微信公众号、微信小程序appId
     * @return
     */
    boolean clearWxCardTicket(String appId);

    /**
     * 功能描述：更新微信accessToken
     *
     * @param appId 微信公众号、微信小程序appId
     *
     * @param secret 微信公众号、微信小程序secret
     *
     * @return 是否更新成功
     *
     */
    boolean updateAccessToken(String appId, String secret);

    /**
     * 功能描述：强制更新微信accessToken
     *
     * @param appId 微信公众号、微信小程序appId
     *
     * @param secret 微信公众号、微信小程序secret
     *
     * @return 是否更新成功
     *
     */
    boolean forceUpdateAccessToken(String appId, String secret);


    void compareAndUpdateAccessToken(String oldAccessToken, String appId, String secret);
}
