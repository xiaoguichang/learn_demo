package com.xiaogch.wechat.common;

/**
 * ProjectName: gfw-parent<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: gfw-parent <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/4/13 15:35 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */

import com.alibaba.fastjson.JSONObject;
import com.xiaogch.common.http.exception.HttpRequestException;
import com.xiaogch.common.http.util.HttpRequestUtil;
import com.xiaogch.wechat.common.dto.PushReqDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * ProjectName: wechat-management-parent<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: wechat-management-parent <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/4/12 17:15 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */

public abstract class AbstractPushMessageService {

    Logger logger = LoggerFactory.getLogger(AbstractPushMessageService.class);

    private static final String SEND_TEMPLATE_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=";

    /**
     * 推送消息
     * @param reTryTimes 失败尝试次数
     * @param pushReqDTO 推送实体
     * @return
     */
    public boolean push(int reTryTimes , Integer userId , PushReqDTO pushReqDTO) {

        Assert.notNull(pushReqDTO , "pushReqDTO must be not null");

        String formId = getFormId(userId);
        if (formId == null) {
            logger.warn("customer don't have available formId , userId={}" , userId);
            return false;
        }
        pushReqDTO.setFormId(formId);
        String errCode = "";
        while (reTryTimes > 0 && !"0".equals(errCode)) {
            JSONObject responseJson ;
            try {
                // form_id不正确，或者过期
                // form_id已被使用
                // access_token过期
                logger.info("send wechat msg error  errCode=" + errCode);
                if ("40001".equals(errCode)) {
                    clearAccessToken(getAppId());
                    responseJson = sendMessageToUser(pushReqDTO);
                    logger.info("push message response is {}" , responseJson );
                    errCode = responseJson.getString("errcode");
                } else if ("41028".equals(errCode) || "41029".equals(errCode)) {
                    formId = getFormId(userId);
                    if (formId == null) {
                        logger.warn("customer don't have available formId , userId={}" , userId);
                        break;
                    }
                    pushReqDTO.setFormId(formId);
                    responseJson = sendMessageToUser(pushReqDTO);
                    logger.info("push message response is {}" , responseJson );
                    errCode = responseJson.getString("errcode");
                    logger.warn("customer don't have available formId , userId={}" , userId);
                } else {
                    responseJson = sendMessageToUser(pushReqDTO);
                    logger.info("push message response is {}" , responseJson );
                    errCode = responseJson.getString("errcode");
                }
            } catch (Exception e) {
                logger.error(e.getMessage() , e);
            }
            reTryTimes --;
        }

        return "0".equals(errCode);
    }


    /***
     * 功能描述：发送推送消息
     *
     * @param pushReqDTO 微信用户标识
     *
     * @return
     *
     * @author guich
     * @since 2017/10/24
     * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    private JSONObject sendMessageToUser(PushReqDTO pushReqDTO) throws HttpRequestException {
//        touser	是	接收者（用户）的 openid
//        template_id	是	所需下发的模板消息的id
//        page	否	点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
//        form_id	是	表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id
//        data	是	模板内容，不填则下发空模板
//        color	否	模板内容字体的颜色，不填默认黑色
//        emphasis_keyword	否	模板需要放大的关键词，不填则默认无放大

        String accessToken = getAccessToken(getAppId() , getSecret());
        String url = SEND_TEMPLATE_MESSAGE_URL + accessToken;

        JSONObject postData = pushReqDTO.toJson();
        JSONObject responseJson = HttpRequestUtil.postRequestByJson(url, postData, 2 * 60 * 1000, 2 * 60 * 1000);
        logger.info("url={} , postData={} , responseJson={}", url, postData, responseJson);

        return responseJson;
    }

    protected abstract String getAppId();

    protected abstract String getSecret();

    protected abstract boolean clearAccessToken(String appId);

    protected abstract String getAccessToken(String appId , String secret);

    protected abstract String getFormId(Integer userId);
}