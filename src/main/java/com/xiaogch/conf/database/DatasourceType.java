package com.xiaogch.conf.database;

/**
 * Created by Administrator on 2018/2/1 0001.
 */
public enum DatasourceType {
    SLAVE("salve" , "从库"),

    MASTER("master" , "主库");
    private String type;
    private String name;
    DatasourceType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
