package com.xiaogch.zk;

import com.google.common.net.HostAndPort;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryForever;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/18 17:30 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class ZkServiceRegister {

    private static Logger LOGGER = LogManager.getLogger(ZkServiceRegister.class);

    private String connectUrl = "127.0.0.1:2181";
    private String basePath;
    private CuratorFramework framework;


    public ZkServiceRegister(String basePath) {
        this.basePath = basePath;
        RetryPolicy retryPolicy = new RetryForever(1000);
        framework = CuratorFrameworkFactory.builder()
                .connectString(connectUrl)
                .connectionTimeoutMs(30*1000)
                .sessionTimeoutMs(30*1000)
                .retryPolicy(retryPolicy)
                .build();
        LOGGER.info("start framework begin ......");
        framework.start();
        LOGGER.info("start framework end ......");
    }


    private boolean registerService(ServiceInfo serviceInfo){
        try {
            String path = basePath + "/" + serviceInfo.getServiceName();
            // 判断basePath 是否已经存在
            Stat stat = framework.checkExists().forPath(path);
            LOGGER.info("############# registerService checkExists basePath={} , stat={}" , path , stat);
            boolean parentExist = false;
            if (stat == null) {
                try {
                    String value = framework.create()
                            .creatingParentsIfNeeded()
                            .withMode(CreateMode.PERSISTENT)
                            .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath(path);
                    LOGGER.info("############# registerService create path={} success" , path);
                    parentExist = true;
                } catch (Exception e) {
                    LOGGER.error("############# registerService create path=" + path + " exception , " + e.getMessage(), e);
                    if (e instanceof KeeperException.NodeExistsException) {
                        parentExist = true;
                    }
                }
            } else {
                parentExist = true;
            }


            try {
                path = path + "/" + serviceInfo.getHostAndPort().getHost() + "_"
                        + serviceInfo.getHostAndPort().getPort() + "_" + serviceInfo.getPid();
                String value = framework.create()
                        .creatingParentsIfNeeded()
                        .withMode(CreateMode.EPHEMERAL)
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath(path);
                LOGGER.info("############# registerService create path={} success" , basePath);
                parentExist = true;
            } catch (Exception e) {
                LOGGER.error("############# registerService create path=" + basePath + " exception , " + e.getMessage(), e);
                if (e instanceof KeeperException.NodeExistsException) {
                    parentExist = true;
                }
            }

            framework.getConnectionStateListenable().addListener(new ZkServiceRegisterConnectionStateListener(serviceInfo , basePath));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    class ZkServiceRegisterConnectionStateListener implements ConnectionStateListener {
        Logger logger = LogManager.getLogger(ZkServiceRegisterConnectionStateListener.class);
        private ServiceInfo serviceInfo;
        private String basePath;
        AtomicInteger lostConnectCounter = new AtomicInteger();
        public ZkServiceRegisterConnectionStateListener(ServiceInfo serviceInfo, String basePath) {
            this.serviceInfo = serviceInfo;
            this.basePath = basePath;
        }

        /**
         * Called when there is a state change in the connection
         *
         * @param client   the client
         * @param newState the new state
         */
        @Override
        public void stateChanged(CuratorFramework client, ConnectionState newState) {
            // 重连状态
            if (Objects.equals(newState , ConnectionState.RECONNECTED)) {
                logger.info("############# zookeeper reconnected deal begin");
                String path = basePath + "/" + serviceInfo.getServiceName()
                        + "/" + serviceInfo.getHostAndPort().getHost() + "_"
                        + serviceInfo.getHostAndPort().getPort() + "_" + serviceInfo.getPid();
                try {
                    Stat stat = client.checkExists().forPath(path);
                    // 数据已经丢失
                    if (stat == null) {
                        try {
                            client.create().creatingParentsIfNeeded()
                                    .withMode(CreateMode.EPHEMERAL)
                                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                                    .forPath(path);
                            logger.info("############# retry register service path={} success." , path);
                        } catch (Exception e) {

                        }
                    } else {
                        logger.info("");
                    }
                } catch (Exception e) {
                    logger.error("############# retry register service path=" + path + " happen exception , " + e.getMessage() , e);
                }
                logger.info("############# zookeeper reconnected deal end");
            }

            // 重连状态
            if (Objects.equals(newState , ConnectionState.LOST)) {
                lostConnectCounter.incrementAndGet();
                logger.info("############# zookeeper lost connection lost times={} " , lostConnectCounter.get());
            }


        }
    }

    class ServiceInfo {

        private Integer pid;
        private Date registerTime;
        private String serviceName;
        private HostAndPort hostAndPort;

        public Integer getPid() {
            return pid;
        }

        public void setPid(Integer pid) {
            this.pid = pid;
        }

        public Date getRegisterTime() {
            return registerTime;
        }

        public void setRegisterTime(Date registerTime) {
            this.registerTime = registerTime;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public HostAndPort getHostAndPort() {
            return hostAndPort;
        }

        public void setHostAndPort(HostAndPort hostAndPort) {
            this.hostAndPort = hostAndPort;
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ZkServiceRegister zkServiceRegister = new ZkServiceRegister("/zk/test");
        zkServiceRegister.registerService(null);
        Thread.sleep(10000000);
    }
}
