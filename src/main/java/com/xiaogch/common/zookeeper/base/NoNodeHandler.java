package com.xiaogch.common.zookeeper.base;

import org.apache.logging.log4j.LogManager;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.logging.log4j.Logger;


public class NoNodeHandler {

    static Logger logger = LogManager.getLogger(NoNodeHandler.class);

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
