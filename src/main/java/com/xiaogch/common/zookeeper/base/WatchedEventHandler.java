package com.xiaogch.common.zookeeper.base;

import org.apache.logging.log4j.LogManager;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.logging.log4j.Logger;

public abstract class WatchedEventHandler {

    Logger logger = LogManager.getLogger(WatchedEventHandler.class);

    protected ZooKeeper zooKeeper;

    protected String path;

    NoNodeHandler noNodeHandler = new NoNodeHandler();

    public void process(WatchedEvent watchedEvent) {
        logger.info("{}" , watchedEvent);
        Watcher.Event.EventType eventType = watchedEvent.getType();
        switch (eventType) {
            case None:
                noneProcess();
                break;
            case NodeCreated:
                nodeCreatedProcess();
                break;
            case NodeDeleted:
                nodeDeletedProcess();
                break;
            case NodeDataChanged:
                nodeDataChangedProcess();
                break;
            case NodeChildrenChanged:
                nodeChildrenChangedProcess();
                break;
            default:
                logger.info("unknown eventType {}" , eventType);
                break;
        }

        registerWatcher();
    }

    public abstract void nodeCreatedProcess();

    public abstract void nodeDeletedProcess();

    public abstract void nodeChildrenChangedProcess();

    public abstract void noneProcess();

    public abstract void nodeDataChangedProcess();

    public abstract void registerWatcher();

    public abstract void clear();

    public void exceptionHandle(Exception e)  {
        if (e instanceof InterruptedException) {
            Thread.interrupted();
        } else if (e instanceof KeeperException ) {
            keeperExceptionHandle((KeeperException)e);
        }
     }

    public void keeperExceptionHandle(KeeperException e) {
        logger.info("KeeperException {}" , e.getMessage());
        if (e.code() == KeeperException.Code.NONODE) {
            // 清数据
            clear();
            noNodeHandler.process(this);
        }
    }

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public String getPath() {
        return path;
    }
}
