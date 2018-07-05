package com.xiaogch.common.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by Administrator on 2018/2/8 0008.
 */

//@Component
public class ZookeeperClient {

    static Logger LOGGER = LogManager.getLogger(ZookeeperClient.class);
    @Autowired
    ZookeeperConfig zookeeperConfig;

    @Autowired
    Environment environment;

    private CuratorFramework curatorFramework;


    @PostConstruct
    public void registerServer() throws Exception {
        LOGGER.info("registerServer() begin ....");
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000 , 3);
        curatorFramework = CuratorFrameworkFactory.builder().
                connectString(zookeeperConfig.getConnectStr())
                .sessionTimeoutMs(zookeeperConfig.getSessionTimeout())
                .connectionTimeoutMs(zookeeperConfig.getConnectTimeout())
                .retryPolicy(retryPolicy)
                .build();
        curatorFramework.start();

        Stat stat = curatorFramework.checkExists().forPath(zookeeperConfig.getServerPath());
        LOGGER.info("stat={}" , stat);
        if (stat == null) {
            try {
                LOGGER.info("path={} node not exists , program create it !", zookeeperConfig.getServerPath());
//                String port = environment.getProperty("server.port");
                curatorFramework.create()
                        .creatingParentsIfNeeded()
                        .withMode(CreateMode.EPHEMERAL)
                        .forPath(zookeeperConfig.getServerPath());
                LOGGER.info("path={} create success" , zookeeperConfig.getServerPath());
            } catch (Exception e) {
                LOGGER.error("create path={} exception" , e);
            }
        }
    }

    @PreDestroy
    public void destroy() {
        curatorFramework.close();
    }


//
//    public String getLoacalIp(){
//        try {
//            Socket socket = new Socket();
//            SocketAddress socketAddress = new InetSocketAddress("localhost" , 8081);
//            socket.connect(socketAddress , 1000);
//            return socket.getLocalAddress().getHostAddress();
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage() , e);
//        }
//
//        try {
//            Enumeration<> networkInterfaces = NetworkInterface.getNetworkInterfaces();
//            if (networkInterfaces != null) {
//                while (networkInterfaces.hasMoreElements()) {
//                    NetworkInterface networkInterface = networkInterfaces.nextElement();
//                    List<InterfaceAddress> interfaceAddressList = networkInterface.getInterfaceAddresses();
//                    int index = 0;
//                    for (InterfaceAddress interfaceAddress : interfaceAddressList) {
//                        LOGGER.info("interfaceAddressList[{}] address {}" , index , interfaceAddress.getAddress().getHostAddress());
//                    }
//                    if (!networkInterface.isVirtual()) {
//                        break;
//                    }
//
//                    if (networkInterface.isLoopback()) {
//
//                    }
//                }
//            }
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage() , e);
//        }
//
//
//        return null;
//    }

}
