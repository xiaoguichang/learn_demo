package com.xiaogch.common.zookeeper.base;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class GetDataHandler extends WatchedEventHandler {

    private Stat stat = new Stat();

    private byte[] data;

    Logger logger = LogManager.getLogger(GetDataHandler.class);

    public GetDataHandler(ZooKeeper zooKeeper , String path) {
        this.zooKeeper = zooKeeper;
        this.path = path;
        getDataFromZookeeper();
    }

    public void getDataFromZookeeper() {
        try {
            data = zooKeeper.getData(path, (event) -> this.process(event), stat);
            logger.info("{} , data {}" , stat , new String(data));
        } catch (Exception e) {
            exceptionHandle(e);
        }
    }

    public synchronized void nodeDataChangedProcess() {
        Stat stat = new Stat();
        try {
            data = zooKeeper.getData(path , false , stat);
            logger.info("{} , data {}" , stat , new String(data));
            // todo data changed how to handle
        } catch (Exception e) {
            exceptionHandle(e);
        }
    }


    @Override
    public synchronized void nodeCreatedProcess() {
        logger.info("nodeCreated Process");
    }

    @Override
    public synchronized void noneProcess() {
        logger.info("none Process");
    }

    @Override
    public synchronized void nodeChildrenChangedProcess() {
        logger.info("nodeChildrenChanged Process");
    }

    @Override
    public synchronized void nodeDeletedProcess() {
        logger.info("nodeDeleted Process");
    }

    @Override
    public synchronized void registerWatcher() {
        try {
            zooKeeper.getData(path, (WatchedEvent event) -> this.process(event) , stat);
        } catch (Exception e) {
            exceptionHandle(e);
        }
    }

    public Stat getStat() {
        return stat;
    }

    public byte[] getData() {
        return Arrays.copyOf(data , data.length);
    }

    @Override
    public synchronized void clear() {
        data = new byte[0];
    }
}
