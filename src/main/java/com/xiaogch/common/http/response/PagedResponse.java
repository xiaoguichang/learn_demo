package com.xiaogch.common.http.response;

import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018/2/4 0004.
 */
public class PagedResponse<T> implements Serializable {

    private int offset;
    private int size;
    private List<T> list;

    public PagedResponse(int offset, List<T> list) {
        Assert.isTrue(offset >= 0 , "offset must great than or equal zero !");
        if (list != null && !list.isEmpty()) {
            this.offset = offset + list.size();
            this.size = list.size();
            this.list = list;
        } else {
            this.offset = offset + 1;
            this.size = 0;
            this.list = Collections.emptyList();
        }
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
