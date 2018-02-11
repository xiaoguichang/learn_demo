package com.xiaogch.common.zookeeper.base;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created by Administrator on 2018/2/11 0011.
 */
public class GetChildrenWatchHandler {
    ZooKeeper zooKeeper;

    String path;

    Logger logger = LoggerFactory.getLogger(GetChildrenWatchHandler.class);

    public GetChildrenWatchHandler(ZooKeeper zooKeeper , String path) {
        this.zooKeeper = zooKeeper;
        this.path = path;
    }

    public List<String> getChildren() {
        try {
            return zooKeeper.getChildren(path, (WatchedEvent event) -> {
                this.proccess(event);
            });
        } catch (Exception e) {
            if (e instanceof InterruptedException) {
                Thread.interrupted();
            }
            throw new RuntimeException(e);
        }
    }

    public void proccess (WatchedEvent watchedEvent) {
        logger.info("{}" , watchedEvent);
        EventType eventType = watchedEvent.getType();
        switch (eventType) {
            case None:
                noneProcess(watchedEvent.getPath());
                break;
            case NodeCreated:
                nodeCreatedProcess(watchedEvent.getPath());
                break;
            case NodeDeleted:
                nodeDeletedProcess(watchedEvent.getPath());
                break;
            case NodeDataChanged:
                nodeDataChangedProcess(watchedEvent.getPath());
                break;
            case NodeChildrenChanged:
                nodeChildrenChangedProcess(watchedEvent.getPath());
                break;
            default:
                logger.info("unknown eventType {}" , eventType);
                break;
        }

        try {
            zooKeeper.getChildren(watchedEvent.getPath(), (WatchedEvent event) -> {
                this.proccess(event);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void nodeCreatedProcess(String path) {
        logger.info("nodeCreated process");
    }

    public void nodeDeletedProcess(String path) {

    }

    public void nodeChildrenChangedProcess(String path) {
        logger.info("nodeChildrenChanged process");

    }

    public void noneProcess(String path) {
        logger.info("none process");
    }

    public void nodeDataChangedProcess(String path) {
        logger.info("nodeDataChanged process");
    }


}
