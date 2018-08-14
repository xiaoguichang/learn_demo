package com.xiaogch.wechat.common.dto;

/**
 * ProjectName: wechat-management-parent<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: wechat-management-parent <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/4/11 15:04 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class FormIdDTO {

    private String fromId;
    private Long createTime;
    private String createTimeStr;
    private Long expiredTime;
    private String expiredTimeStr;

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getExpiredTimeStr() {
        return expiredTimeStr;
    }

    public void setExpiredTimeStr(String expiredTimeStr) {
        this.expiredTimeStr = expiredTimeStr;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FormIdDTO{");
        sb.append("fromId='").append(fromId).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", createTimeStr='").append(createTimeStr).append('\'');
        sb.append(", expiredTime=").append(expiredTime);
        sb.append(", expiredTimeStr='").append(expiredTimeStr).append('\'');
        sb.append('}');
        return sb.toString();
    }
}