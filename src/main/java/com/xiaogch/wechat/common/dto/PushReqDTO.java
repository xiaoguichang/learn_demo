package com.xiaogch.wechat.common.dto;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/14 19:30 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class PushReqDTO {
//    touser	是	接收者（用户）的 openid
//    template_id	是	所需下发的模板消息的id
//    page	否
//    form_id	是
//    data	是	模板内容，不填则下发空模板
//    color	否	模板内容字体的颜色，不填默认黑色 【废弃】
//    emphasis_keyword	否	模板需要放大的关键词，不填则默认无放大

    /** 接收者（用户）的 openid 必填*/
    private String toUser;

    /** 所需下发的模板消息的id 必填*/
    private String templateId;

    /** 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。*/
    private String page;

    /** 表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id 必填*/
    private String formId;

    /** 模板内容，不填则下发空模板 必填*/
    private Map<String , Object> data;

    /** 模板内容字体的颜色，不填默认黑色 【废弃】*/
    private String color;

    /** 模板需要放大的关键词，不填则默认无放大*/
    private String emphasisKeyword;

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEmphasisKeyword() {
        return emphasisKeyword;
    }

    public void setEmphasisKeyword(String emphasisKeyword) {
        this.emphasisKeyword = emphasisKeyword;
    }

    //    {
//        "touser": "OPENID",
//        "template_id": "TEMPLATE_ID",
//        "page": "index",
//        "form_id": "FORMID",
//        "data": {
//            "keyword1": {
//                "value": "339208499"
//            },
//            "keyword2": {
//                "value": "2015年01月05日 12:30"
//            },
//            "keyword3": {
//                "value": "粤海喜来登酒店"
//            },
//            "keyword4": {
//                "value": "广州市天河区天河路208号"
//            }
//        },
//        "emphasis_keyword": "keyword1.DATA"
//    }

    public JSONObject toJson() {
        Assert.hasText(toUser , "toUser must be not null or not empty");
        Assert.hasText(templateId , "templateId must be not null or not empty");
        Assert.notNull(data , "data must be not null or not empty");
        Assert.hasText(formId , "formId must be not null or not empty");
        JSONObject resultJson = new JSONObject();
        resultJson.put("touser" , toUser);
        resultJson.put("template_id" , templateId);
        resultJson.put("page" , toUser);
        resultJson.put("form_id" , formId);
        resultJson.put("data" , data);
        if (StringUtils.hasText(emphasisKeyword)) {
            resultJson.put("emphasis_keyword" , emphasisKeyword);
        }

        if (StringUtils.hasText(page)) {
            resultJson.put("page" , page);
        }
        return resultJson;
    }

}
