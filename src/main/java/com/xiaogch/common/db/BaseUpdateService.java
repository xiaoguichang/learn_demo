package com.xiaogch.common.db;

import java.io.Serializable;

public interface BaseUpdateService<D extends BaseDAO<PK , T>, T, PK extends Serializable> {

    int updateByPrimaryKeySelective(T t);
}
