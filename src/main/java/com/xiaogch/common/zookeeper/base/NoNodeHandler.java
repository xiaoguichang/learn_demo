package com.xiaogch.common.zookeeper.base;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/2/12 15:49 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
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
