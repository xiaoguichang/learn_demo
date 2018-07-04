package com.xiaogch.pool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/4 12:28 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class MyPooledObjectFactory<T> extends BasePooledObjectFactory<T> {


    @Override
    public T create() throws Exception {
        return null;
    }

    @Override
    public PooledObject<T> wrap(T t) {
        return new DefaultPooledObject<>(t);
    }
}
