package com.xiaogch.wechat.common.dto;

import java.text.SimpleDateFormat;

public class TicketDto {

    private String appId;
    private String ticket;
    private Integer expiresIn;
    private Long createTimestamp;
    private Long expireTimeStamp;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Long getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Long createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public Long getExpireTimeStamp() {
        return expireTimeStamp;
    }

    public void setExpireTimeStamp(Long expireTimeStamp) {
        this.expireTimeStamp = expireTimeStamp;
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder("AccessTokenEntitiy{")
                .append("appId='").append(appId).append("' ")
                .append("ticket='").append(ticket).append("' ")
                .append("expiresIn='").append(expiresIn).append("' ")
                .append("createTimestamp='").append(createTimestamp).append("' ")
                .append("expireTimeStamp='").append(expireTimeStamp).append("' ")
                .append("createTime='").append(createTimestamp == null ? "" : simpleDateFormat.format(createTimestamp)).append("' ")
                .append("expireTime='").append(expireTimeStamp == null ? "" : simpleDateFormat.format(expireTimeStamp)).append("'}");
        return sb.toString();
    }

}