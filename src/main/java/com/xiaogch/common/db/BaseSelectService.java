package com.xiaogch.common.db;

import java.io.Serializable;

public interface BaseSelectService<D extends BaseDAO<PK , T>, T, PK extends Serializable> {

    T queryById(Integer id);


}
