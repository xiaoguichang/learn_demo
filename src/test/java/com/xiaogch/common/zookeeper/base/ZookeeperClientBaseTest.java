package com.xiaogch.common.zookeeper.base;

import com.xiaogch.common.zookeeper.ZookeeperConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by Administrator on 2018/2/11 0011.
 */
public class ZookeeperClientBaseTest {

    Logger logger = LogManager.getLogger(ZookeeperClientBaseTest.class);

    ZookeeperConfig zookeeperConfig;

    @Before
    public void setUp() throws Exception {
        zookeeperConfig = new ZookeeperConfig();
        zookeeperConfig.setConnectStr("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183");
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
        String path = "/system";
        Stat stat = zooKeeper.exists(path , false);
        if (stat == null) {
            zooKeeper.create(path , "hello".getBytes() , ZooDefs.Ids.OPEN_ACL_UNSAFE , CreateMode.PERSISTENT);
        }

        GetChildrenHandler getChildrenWatchHandler = new GetChildrenHandler(zooKeeper , path);
        List<String> list = getChildrenWatchHandler.getChildren();
        for (String child : list) {
//            logger.info("child {}" , child);
        }

        GetDataHandler getDataHandler = new GetDataHandler(zooKeeper , path);
        getDataHandler.getData();

        while (true) {
            Thread.sleep(1000);
        }
    }

}