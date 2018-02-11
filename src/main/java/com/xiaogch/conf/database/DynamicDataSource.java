package com.xiaogch.conf.database;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2018/2/1 0001.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private int readDatasourceSize = 0;

    public int getReadDatasourceSize() {
        return readDatasourceSize;
    }

    public void setReadDatasourceSize(int readDatasourceSize) {
        this.readDatasourceSize = readDatasourceSize;
    }

    public DynamicDataSource(int readDatasourceSize) {
        this.readDatasourceSize = readDatasourceSize;
    }

    public AtomicLong getAtomicLong() {
        return atomicLong;
    }

    public void setAtomicLong(AtomicLong atomicLong) {
        this.atomicLong = atomicLong;
    }

    private AtomicLong atomicLong = new AtomicLong(0);
    @Override
    protected Object determineCurrentLookupKey() {
        DatasourceType datasourceType = DatasourceContextHolder.getDatasourceType();
        // 写库
        if (DatasourceType.MASTER == datasourceType) {
            return DatasourceType.MASTER.getType() + "_0";
        }
        // 没有配置读库， 选择写库
        if (readDatasourceSize == 0 || readDatasourceSize < 0) {
            logger.info("salve datasource not set , use master datasource");
            return DatasourceType.MASTER.getType() + "_0";
        } else {
            long value = atomicLong.getAndAdd(1);
            return DatasourceType.SLAVE.getType() + "_" + (value % readDatasourceSize);
        }
    }
}
