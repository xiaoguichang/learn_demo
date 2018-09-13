package com.xiaogch.common.db;

import java.io.Serializable;

public interface BaseInsertService<D extends BaseDAO<PK , T>, T, PK extends Serializable> {

    /**
     *  插入数据
     * @param t
     * @return
     */
    int insert(T t);

    /**
     *  插入数据
     * @param t
     * @return
     */
    int insertSelective(T t);

}
