package com.xiaogch.common.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.*;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Administrator on 2018/2/8 0008.
 */

@Component
public class ZookeeperClient {

    static  Logger logger = LoggerFactory.getLogger(ZookeeperClient.class);
    @Autowired
    ZookeeperConfig zookeeperConfig;

    @Autowired
    Environment environment;

    private CuratorFramework curatorFramework;


    @PostConstruct
    public void registerServer() throws Exception {

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000 , 3);
        curatorFramework = CuratorFrameworkFactory.builder().
                connectString(zookeeperConfig.getConnectStr())
                .sessionTimeoutMs(zookeeperConfig.getSessionTimeout())
                .connectionTimeoutMs(zookeeperConfig.getConnectTimeout())
                .retryPolicy(retryPolicy)
                .build();
        curatorFramework.start();

        String port = environment.getProperty("server.port");
        String result = curatorFramework.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(zookeeperConfig.getServerPath());

        logger.info("result = {} " , result);
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
//            logger.error(e.getMessage() , e);
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
//                        logger.info("interfaceAddressList[{}] address {}" , index , interfaceAddress.getAddress().getHostAddress());
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
//            logger.error(e.getMessage() , e);
//        }
//
//
//        return null;
//    }

}