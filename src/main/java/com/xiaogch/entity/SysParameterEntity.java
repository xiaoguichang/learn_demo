package com.xiaogch.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018/1/31 0031.
 */
public class SysParameterEntity implements Serializable {

    private Integer id;
    private String code;
    private String value;
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
