/**
 * ProjectName: jobrecommend-service <BR>
 * File name: TokenAndTicketServiceImpl.java <BR>
 * Author: guich  <BR>
 * Project: jobrecommend-service <BR>
 * Version: v 1.0 <BR>
 * Date: 2017/10/24 17:16 <BR>
 * Description: 微信accesstoken、ticket获取服务实现 <BR>
 * Function List: 1、获取微信accesstoken<BR>
 *                2、获取微信WxCardTicket<BR>
 *                3、获取微信JsapiTicket<BR>
 */
package com.xiaogch.wechat.common.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xiaogch.common.http.exception.HttpRequestException;
import com.xiaogch.common.http.util.HttpRequestUtil;
import com.xiaogch.common.redis.RedisException;
import com.xiaogch.common.redis.standalone.RedisStringService;
import com.xiaogch.wechat.common.WechatException;
import com.xiaogch.wechat.common.dto.AccessTokenDto;
import com.xiaogch.wechat.common.dto.TicketDto;
import com.xiaogch.wechat.common.service.TokenAndTicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("tokenAndTicketService")
public class TokenAndTicketServiceImpl implements TokenAndTicketService {

    private static final String GET_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=@accessToken&type=@ticketType";
    private static final String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=@appid&secret=@secret";
    private static final String TICKET_TYPE_WX_CARD = "wx_card";
    private static final String TICKET_TYPE_JSAPI = "jsapi";


    /** 微信accessToken redis key , key = 前缀 + appId*/
    private static final String ACCESS_TOKEN_KEY = "wechat.accesstoken.";
    /** 微信accessToken 刷新锁 reids key , key = 前缀 + appId*/
    private static final String ACCESS_TOKEN_LOCK_KEY = "wechat.accesstoken.lock.";

    /** 微信jsapi ticket redis key , key = 前缀 + appId*/
    private static final String JSAPI_TICKET_KEY = "wechat.jsapi.ticket.";
    /** 微信jsapi ticket 刷新锁 reids key , key = 前缀 + appId*/
    private static final String JSAPI_TICKET_LOCK_KEY = "wechat.jsapi.ticket.lock.";

    /** 微信wx_card ticket redis key , key = 前缀 + appId*/
    private static final String WX_CARD_TICKET_KEY = "wechat.wxcard.ticket.";
    /** 微信wx_card ticket 刷新锁 reids key , key = 前缀 + appId*/
    private static final String WX_CARD_TICKET_LOCK_KEY = "wechat.wxcard.ticket.lock.";

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisStringService redisStringService;


    /***
     * 功能描述：获取微信JsapiTicket
     *
     * @param appId 微信公众号、微信小程序appId
     * @param secret 微信公众号、微信小程序secret
     *
     * @return 成功返回JsapiTicket
     *
     *
     * @author guich
     * @since 2017/10/24
     * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    @Override
    public String getJsapiTicket(String appId, String secret) {
        return getTicket(appId , secret , TICKET_TYPE_JSAPI);
    }

    /***
     * 功能描述：获取微信WxCardTicket，用于微信卡包
     *
     * @param appId 微信公众号、微信小程序appId
     * @param secret 微信公众号、微信小程序secret
     *
     * @return 成功返回WxCardTicket
     *
     *
     * @author guich
     * @since 2017/10/24
     * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    @Override
    public String getWxCardTicket(String appId , String secret) {
        return getTicket(appId , secret , TICKET_TYPE_WX_CARD);
    }

    private String getTicket(String appId , String secret , String ticketType) {
        //从redis中获取Ticket
        TicketDto ticketDto = getTicketFromRedis(appId, ticketType);
        if (ticketDto != null && !isExpired(ticketDto.getExpireTimeStamp())) {
            return ticketDto.getTicket();
        }

        //重新生成Ticket
        ticketDto = genTicket(appId , secret , ticketType);
        return ticketDto.getTicket();
    }

    /**
     * 判断是否过期
     * @param expireTimeStamp 过期时间戳
     * @return
     */
    private boolean isExpired(Long expireTimeStamp) {
        if (expireTimeStamp == null) {
            return true;
        }

        if (System.currentTimeMillis() < expireTimeStamp) {
            return false;
        } else {
            return true;
        }
    }

    /***
     *
     *
     * @param ticketType
     * @return
     */
    public TicketDto getTicketFromRedis(String appId , String ticketType) {
        String keyName = getTicketRedisKey(appId , ticketType , false);
        try {
            return redisStringService.getValue(keyName , TicketDto.class);
        } catch (Throwable e) {
            throw new WechatException("getTicketFromRedis redis exception" , e);
        }
    }

    private TicketDto genTicket(String appId , String secret, String ticketType) {
        String lockName = getTicketRedisKey(appId , ticketType , true);
        String keyName = getTicketRedisKey(appId , ticketType , false);
        try {
            if (!redisStringService.getLock(lockName, 5 * 60 * 1000, 1 * 60 * 1000l)) {
                logger.info("get lock {} failure ", lockName);
                throw new WechatException("get the lock " + lockName + " failure");
            }

            TicketDto ticketDto = getTicketFromRedis(appId , ticketType);
            if (ticketDto != null && !isExpired(ticketDto.getExpireTimeStamp())) {
                return ticketDto;
            }
            String url = GET_TICKET_URL;
            url = url.replaceAll("@accessToken", getAccessToken(appId , secret));
            url = url.replaceAll("@ticketType", ticketType);
            JSONObject responseJson = HttpRequestUtil.getRequestByJson(url);
            logger.info("get ticket from weixin server response={} url={}", responseJson, url);
            if (responseJson != null && responseJson.containsKey("ticket")) {
                TicketDto ticketDtoTmp = new TicketDto();
                ticketDtoTmp.setTicket(responseJson.getString("ticket"));
                ticketDtoTmp.setAppId(appId);
                long currentTime = System.currentTimeMillis();
                ticketDtoTmp.setCreateTimestamp(currentTime);
                int expiresIn = responseJson.getIntValue("expires_in");
                ticketDtoTmp.setExpiresIn(expiresIn);
                //提前五分钟失效
                ticketDtoTmp.setExpireTimeStamp(currentTime + (expiresIn - 5 * 60) * 1000);
                //设置redis信息
                redisStringService.set(keyName , JSONObject.toJSONString(ticketDtoTmp) , (expiresIn-5*60));
                return ticketDtoTmp;
            }
        } catch (Exception e) {
            throw new WechatException("genTicket" , e);
        } finally {
            try {
                redisStringService.releaseLock(lockName);
            } catch (RedisException e) {
                logger.error("genTicket releaseLock lockName=" + lockName + " exception" , e);
            }
        }
        throw new WechatException("genTicket call api failure");
    }

    private String getTicketRedisKey(String appId , String ticketType , boolean isLock) {
        if (TICKET_TYPE_JSAPI.equals(ticketType)) {
            if (!isLock) {
                return JSAPI_TICKET_KEY + appId;
            }
            return JSAPI_TICKET_LOCK_KEY + appId;
        } else if (TICKET_TYPE_WX_CARD.equals(ticketType)){
            if (!isLock) {
                return WX_CARD_TICKET_KEY + appId;
            }
            return WX_CARD_TICKET_LOCK_KEY + appId;
        } else {
            throw new IllegalArgumentException("无效的ticketType，目前支持jsapi、wx_card");
        }
    }

    /***
     * 功能描述：获取微信accessToken
     *
     * @param appId 微信公众号、微信小程序appId
     * @param secret 微信公众号、微信小程序secret
     *
     * @return 成功返回accessToken
     *
     * @throws WechatException
     *
     * @author guich
     * @since 2017/10/24
     * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    @Override
    public String getAccessToken(String appId , String secret) {
        String accessTokenRedisKey = ACCESS_TOKEN_KEY + appId;
        AccessTokenDto accessTokenDto = getAccessTokenFromRedis(accessTokenRedisKey);
        if (accessTokenDto != null && !isExpired(accessTokenDto.getExpireTimeStamp())) {
            return accessTokenDto.getAccessToken();
        }

        return genAccessToken(appId , secret , false).getAccessToken();
    }

    public AccessTokenDto getAccessTokenFromRedis(String accessTokenRedisKey) {
        try {
            return redisStringService.getValue(accessTokenRedisKey , AccessTokenDto.class);
        } catch (RedisException e) {
            throw new WechatException("getAccessTokenFromRedis redis exception" , e);
        }
    }

    private AccessTokenDto genAccessToken(String appId , String secret , boolean forceUpdate) {
        String lockName = ACCESS_TOKEN_LOCK_KEY + appId;
        String keyName = ACCESS_TOKEN_KEY + appId;
        try {
            if (!redisStringService.getLock(lockName , 5*60*1000 , 1*60*1000l)) {
                logger.info("get lock to fresh access_token failure , key={}" , lockName);
                throw new WechatException("get the lock to fresh accessToken failure");
            }

            if (!forceUpdate) {
                AccessTokenDto accessTokenDto = getAccessTokenFromRedis(keyName);
                if (accessTokenDto != null && !isExpired(accessTokenDto.getExpireTimeStamp())) {
                    return accessTokenDto;
                }
            }

            String url = GET_TOKEN_URL;
            url = url.replaceAll("@appid" , appId);
            url = url.replaceAll("@secret" , secret);

            JSONObject responseJson = HttpRequestUtil.getRequestByJson(url);
            logger.info("get access_token from weixin server response={} url={}" , responseJson, url);
            if (responseJson != null && responseJson.containsKey("access_token")) {
                AccessTokenDto accessTokenDto = new AccessTokenDto();
                accessTokenDto.setAccessToken(responseJson.getString("access_token"));
                accessTokenDto.setAppId(appId);
                long currentTime = System.currentTimeMillis();
                accessTokenDto.setCreateTimestamp(currentTime);
                int expiresIn = responseJson.getIntValue("expires_in");
                accessTokenDto.setExpiresIn(expiresIn);
                //提前五分钟失效
                accessTokenDto.setExpireTimeStamp(currentTime + (expiresIn-5*60)*1000);
                //设置redis信息
                redisStringService.set(keyName , JSONObject.toJSONString(accessTokenDto) , (expiresIn-5*60));
                return accessTokenDto;
            }
        } catch (Exception e) {
            throw  wrapGenAccessTokenException("genAccessToken" , e);
        } finally {
            try {
                redisStringService.releaseLock(lockName);
            } catch (RedisException e) {
                logger.error("genAccessToken releaseLock lockName=" + lockName + " exception" , e);
            }
        }
        throw new WechatException("genAccessToken call api failure");
    }

    /**
     * 功能描述：清理微信accessToken
     *
     * @param appId 微信公众号、微信小程序appId
     *
     * @return
     */
    @Override
    public boolean clearAccessToken(String appId) {
        try {
            redisStringService.del(ACCESS_TOKEN_KEY + appId);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage() , e);
        }
        return false;
    }

    /**
     * 功能描述：清理微信JsapiTicket
     *
     * @param appId 微信公众号、微信小程序appId
     *
     * @return
     */
    @Override
    public boolean clearJsapiTicket(String appId) {
        try {
            redisStringService.del(JSAPI_TICKET_KEY + appId);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage() , e);
        }
        return false;
    }

    /**
     * 功能描述：清理微信WxCardTicket，用于微信卡包
     *
     * @param appId 微信公众号、微信小程序appId
     *
     * @return
     */
    @Override
    public boolean clearWxCardTicket(String appId) {
        try {
            redisStringService.del(WX_CARD_TICKET_KEY + appId);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage() , e);
        }
        return false;
    }

    /**
     * 功能描述：更新微信accessToken
     *
     * @param appId 微信公众号、微信小程序appId
     *
     * @param secret 微信公众号、微信小程序secret
     *
     * @return 是否更新成功
     *
     * @throws WechatException
     */
    @Override
    public boolean updateAccessToken(String appId, String secret) {
        return genAccessToken(appId , secret , false) != null;
    }

    /**
     * 功能描述：强制更新微信accessToken
     *
     * @param appId  微信公众号、微信小程序appId
     * @param secret 微信公众号、微信小程序secret
     *
     * @return 是否更新成功
     *
     * @throws WechatException
     */
    @Override
    public boolean forceUpdateAccessToken(String appId, String secret) {
        return genAccessToken(appId , secret , true) != null;
    }

    @Override
    public void compareAndUpdateAccessToken(String oldAccessToken, String appId, String secret) {
        Assert.hasText(oldAccessToken , "oldAccessToken must be not null or empty !");
        String lockName = ACCESS_TOKEN_LOCK_KEY + appId;
        String keyName = ACCESS_TOKEN_KEY + appId;
        try {
            if (!redisStringService.getLock(lockName , 5*60*1000 , 1*60*1000l)) {
                logger.info("get lock to fresh access_token failure , key={}" , lockName);
                throw new WechatException("get the lock to fresh accessToken failure");
            }

            AccessTokenDto accessTokenDto = getAccessTokenFromRedis(keyName);
            if (accessTokenDto != null && !isExpired(accessTokenDto.getExpireTimeStamp())
                    && !oldAccessToken.equals(accessTokenDto.getAccessToken())) {
                return;
            }

            String url = GET_TOKEN_URL;
            url = url.replaceAll("@appid" , appId);
            url = url.replaceAll("@secret" , secret);

            JSONObject responseJson = HttpRequestUtil.getRequestByJson(url);
            logger.info("get access_token from weixin server response={} url={}" , responseJson, url);
            if (responseJson != null && responseJson.containsKey("access_token")) {
                accessTokenDto = new AccessTokenDto();
                accessTokenDto.setAccessToken(responseJson.getString("access_token"));
                accessTokenDto.setAppId(appId);
                long currentTime = System.currentTimeMillis();
                accessTokenDto.setCreateTimestamp(currentTime);
                int expiresIn = responseJson.getIntValue("expires_in");
                accessTokenDto.setExpiresIn(expiresIn);
                //提前五分钟失效
                accessTokenDto.setExpireTimeStamp(currentTime + (expiresIn-5*60)*1000);
                //设置redis信息
                redisStringService.set(keyName , JSONObject.toJSONString(accessTokenDto) , (expiresIn-5*60));
                return;
            }
        } catch (Exception e) {
            throw wrapGenAccessTokenException("compareAndUpdateAccessToken" , e);
        } finally {
            try {
                redisStringService.releaseLock(lockName);
            } catch (RedisException e) {
                logger.error("compareAndUpdateAccessToken releaseLock lockName=" + lockName + " exception" , e);
            }
        }
        throw new WechatException("compareAndUpdateAccessToken call api failure");
    }

    private WechatException wrapGenAccessTokenException(String methodName , Exception e) {
        if (e instanceof HttpRequestException) {
            return new WechatException(methodName + " http request exception,\n" + e.getMessage() , e);
        } else if (e instanceof RedisException) {
            return new WechatException(methodName + " redis exception,\n" + e.getMessage() , e);
        }
        return new WechatException(methodName + " unkown exception,\n" + e.getMessage() , e);
    }
}
