package com.xiaogch.conf.database;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Administrator on 2018/2/1 0001.
 */
public class DatasourceContextHolder {

    private static Logger LOGGER = LogManager.getLogger(DatasourceContextHolder.class);

    private static ThreadLocal<DatasourceType> threadLocal = new ThreadLocal<>();

    public static void selectMaster() {
        LOGGER.debug("select master");
        threadLocal.set(DatasourceType.MASTER);
    }

    public static void selectSlave() {
        LOGGER.debug("select slave");
        threadLocal.set(DatasourceType.SLAVE);
    }

    public static DatasourceType getDatasourceType() {
        return threadLocal.get();
    }

    public static void clearDatasourceType(){ threadLocal.remove(); }

}
