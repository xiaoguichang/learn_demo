package com.xiaogch.common.db;


import java.io.Serializable;

public interface BaseDeleteService<D extends BaseDAO<PK , T>, T, PK extends Serializable> {

    /**
     *  删除数据
     * @param t
     * @return
     */
    int delete(T t);
}
