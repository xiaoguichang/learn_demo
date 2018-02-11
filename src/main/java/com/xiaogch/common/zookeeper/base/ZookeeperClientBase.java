package com.xiaogch.common.zookeeper.base;

import com.xiaogch.common.zookeeper.ZookeeperConfig;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2018/2/11 0011.
 */

@Component
public class ZookeeperClientBase {

    Logger logger = LoggerFactory.getLogger(ZookeeperClientBase.class);

    @Autowired
    ZookeeperConfig zookeeperConfig;

    public ZookeeperClientBase(ZookeeperConfig zookeeperConfig) {
        this.zookeeperConfig = zookeeperConfig;
    }

    public ZooKeeper getZookeeper() throws IOException, InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper(zookeeperConfig.getConnectStr() , zookeeperConfig.getSessionTimeout() , (WatchedEvent watchedEvent) -> {
            KeeperState keeperState = watchedEvent.getState();
            logger.info("keeperState {}" , keeperState);
            switch (keeperState) {
                case Expired:
                    logger.info("zookeeper connection has expired !");
                    break;
                case AuthFailed:
                    logger.info("zookeeper connection create failure because AuthFailed !");
                    break;
                case Disconnected:
                    logger.info("zookeeper connection has Disconnected !");
                    break;
                case SyncConnected:
                    logger.info("zookeeper connection has created !");
                    countDownLatch.countDown();
                    break;
                case ConnectedReadOnly:
                    logger.info("zookeeper connection with read only has created !");
                    countDownLatch.countDown();
                    break;
                case SaslAuthenticated:
                    logger.info("zookeeper connection with SASL-authorized has created !");
                    countDownLatch.countDown();
                    break;
                default:
                    throw new RuntimeException("invalid zookeeper state");
            }
            if (keeperState == KeeperState.SyncConnected) {

            }
        });
        countDownLatch.await();
        return zooKeeper;
    }


}
