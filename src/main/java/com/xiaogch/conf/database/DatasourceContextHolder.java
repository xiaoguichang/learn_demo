package com.xiaogch.conf.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2018/2/1 0001.
 */
public class DatasourceContextHolder {

    private static Logger logger = LoggerFactory.getLogger(DatasourceContextHolder.class);

    private static ThreadLocal<DatasourceType> threadLocal = new ThreadLocal<>();

    public static void selectMaster() {
        logger.debug("select master");
        threadLocal.set(DatasourceType.MASTER);
    }

    public static void selectSlave() {
        logger.debug("select slave");
        threadLocal.set(DatasourceType.SLAVE);
    }

    public static DatasourceType getDatasourceType() {
        return threadLocal.get();
    }

}
