package com.xiaogch.common.zookeeper.base;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoNodeHandler {

    Logger logger = LoggerFactory.getLogger(NoNodeHandler.class);

    public NoNodeHandler() {
    }

    public void process(WatchedEventHandler watchedEventHandler) {
        logger.info("{}" , watchedEventHandler.getClass());
        ZooKeeper zooKeeper = watchedEventHandler.getZooKeeper();
        try {
            zooKeeper.exists(watchedEventHandler.getPath() , event -> {
                logger.debug("{}" , event);
                if (event.getType() == Watcher.Event.EventType.NodeCreated) {
                    watchedEventHandler.registerWatcher();
                }
            });
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
