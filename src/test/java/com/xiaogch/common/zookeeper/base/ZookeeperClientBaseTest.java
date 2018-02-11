package com.xiaogch.common.zookeeper.base;

import com.xiaogch.common.zookeeper.ZookeeperConfig;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Administrator on 2018/2/11 0011.
 */
public class ZookeeperClientBaseTest {

    Logger logger = LoggerFactory.getLogger(ZookeeperClientBaseTest.class);

    ZookeeperConfig zookeeperConfig;

    @Before
    public void setUp() throws Exception {
        zookeeperConfig = new ZookeeperConfig();
        zookeeperConfig.setConnectStr("127.0.0.1:2181");
        zookeeperConfig.setSessionTimeout(60*1000);
        zookeeperConfig.setReconnectTimes(60*1000);
    }

    @Test
    public void getZookeeper() throws Exception {
        ZookeeperClientBase zookeeperClientBase = new ZookeeperClientBase(zookeeperConfig);
        ZooKeeper zooKeeper = zookeeperClientBase.getZookeeper();
        logger.info("connection sessionId {}" , zooKeeper.getSessionId());
        logger.info("connection sessionPassword {}" , zooKeeper.getSessionPasswd());
        logger.info("connection state {}" , zooKeeper.getState());

        GetChildrenWatchHandler getChildrenWatchHandler = new GetChildrenWatchHandler(zooKeeper , "/system");
        List<String> list = getChildrenWatchHandler.getChildren();
        for (String child : list) {
            logger.info("child {}" , child);
        }

        while (true) {
            Thread.sleep(1000);
        }
    }

}