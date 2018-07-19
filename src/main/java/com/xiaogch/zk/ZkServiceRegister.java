package com.xiaogch.zk;

import com.alibaba.fastjson.JSONObject;
import com.google.common.net.HostAndPort;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.RetryForever;
import org.apache.http.conn.util.InetAddressUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private ServiceInfo serviceInfo;

    private ExecutorService executorService = Executors.newFixedThreadPool(1);

    public ZkServiceRegister(String basePath , ServiceInfo serviceInfo) {
        this.basePath = basePath + "/" + serviceInfo.getServiceName();
        this.serviceInfo = serviceInfo;
        RetryPolicy retryPolicy = new RetryForever(1000);
        framework = CuratorFrameworkFactory.builder()
                .connectString(connectUrl)
                .connectionTimeoutMs(30*1000)
                .sessionTimeoutMs(30*1000)
                .retryPolicy(retryPolicy)
                .build();
        LOGGER.info("############# start zookeeper client connectUrl={} begin." , connectUrl);
        framework.start();
        LOGGER.info("############# start zookeeper client connectUrl={} end.", connectUrl);
    }


    /**
     * 注册服务，如果失败会一直尝试注册，直到注册成功为止
     * @param retryInterval 重试时间间隔
     */
    public void registerServiceWithRetryForever(int retryInterval) {
        executorService.submit(()->{
            while (!createBasePath()) {
                LOGGER.info("############# registerServiceWithRetryForever createBasePath failure");
                try {
                    Thread.sleep(retryInterval);
                } catch (InterruptedException e) {
                    LOGGER.error(e.getMessage() , e);
                }
            }
            LOGGER.info("############# registerServiceWithRetryForever createBasePath success");
            // 注册服务节点
            while (!registerServiceNode()) {
                LOGGER.info("############# registerServiceWithRetryForever try registerServiceNode failure");
                try {
                    Thread.sleep(retryInterval);
                } catch (InterruptedException e) {
                    LOGGER.error(e.getMessage() , e);
                }
            }
            LOGGER.info("############# registerServiceWithRetryForever registerServiceNode success");
            // 增加链接状态监听器
            addConnectionStateListener(retryInterval);
        });

    }


    /**
     * 注册服务，只会注册一次，不会重复尝试
     * @param reconnectRetryInterval 重连重新注册重试时间间隔
     */
    public void registerServiceOnlyOneTime(int reconnectRetryInterval){
        if (!createBasePath()) {
            LOGGER.info("############# registerServiceOnlyOneTime createBasePath failure , stop  register service");
            return;
        }
        boolean registerServiceNodeResult = registerServiceNode();
        LOGGER.info("############# registerServiceOnlyOneTime registerServiceNode {}", registerServiceNodeResult ? "success" : "failure");
        // 创建成功注册
        if (registerServiceNodeResult) {
            addConnectionStateListener(reconnectRetryInterval);
        }
    }

    private String getServiceNodePath() {
        return basePath + "/" + serviceInfo.getHostAndPort().getHost() + ":"
                + serviceInfo.getHostAndPort().getPort() + "@" + serviceInfo.getPid();
    }

    /**
     * 添加链接状态监听器
     * @param reconnectRetryInterval 重连重新注册重试时间间隔
     */
    private void addConnectionStateListener(int reconnectRetryInterval) {
        framework.getConnectionStateListenable().addListener((client, newState) -> {
            String serviceNodePath = getServiceNodePath();
            // 重连状态
            if (Objects.equals(newState , ConnectionState.RECONNECTED)) {
                LOGGER.info("############# zookeeper reconnected deal begin");
                Stat existStat = null;
                try {
                    existStat = client.checkExists().forPath(serviceNodePath);
                } catch (Exception e) {
                    LOGGER.error("############# zookeeper reconnected  checkExists path=" + serviceNodePath + " happen exception , " + e.getMessage() , e);
                }

                // 数据已经丢失
                if (existStat == null) {
                    executorService.submit(()->{
                        while (!registerServiceNode()) {
                            LOGGER.info("############# zookeeper reconnected try register service failure");
                            try {
                                Thread.sleep(reconnectRetryInterval);
                            } catch (InterruptedException e) {
                                LOGGER.error(e.getMessage() , e);
                            }
                        }
                    });
                } else {
                    LOGGER.info("############# lost connection path={} exist" , serviceNodePath);
                }
                LOGGER.info("############# zookeeper reconnected deal end");
            }

            // 重连状态
            if (Objects.equals(newState , ConnectionState.LOST)) {
                LOGGER.info("############# zookeeper lost connection lost");
            }
        });
    }

    /**
     * 创建基本路径
     * @return
     */
    public boolean createBasePath() {
        Stat stat = null;
        try {
            stat = framework.checkExists().forPath(basePath);
        } catch (Exception e) {
            LOGGER.error("############# checkExists path=" + basePath + " exception: " + e.getMessage() , e);
        }
        // 判断basePath 是否已经存在
        LOGGER.info("############# registerService checkExists basePath={} , stat={}" , basePath , stat);
        if (stat == null) {
            // 创建basePath
            try {
                framework.create().creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath(basePath);
                LOGGER.info("############# registerService create basePath={} success" , basePath);
            } catch (Exception e) {
                LOGGER.error("############# registerService create basePath=" + basePath + " exception: " + e.getMessage(), e);
                if (e instanceof KeeperException.NodeExistsException) {
                    LOGGER.info("############# registerService create basePath={} success" , basePath);
                } else {
                    LOGGER.info("############# registerService create basePath={} failure" , basePath);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 注册服务节点
     * @return
     */
    public boolean registerServiceNode(){
        String path = getServiceNodePath();
        try {
            String nodeData = JSONObject.toJSONStringWithDateFormat(serviceInfo , "yyyy-MM-dd HH:mm:ss");
            framework.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath(path , nodeData.getBytes("utf-8"));
            LOGGER.info("############# registerServiceNode create path={} , data={} success" , path ,nodeData);
        } catch (Exception e) {
            LOGGER.error("############# registerServiceNode create path=" + path + " exception: " + e.getMessage(), e);
            if (e instanceof KeeperException.NodeExistsException) {
                LOGGER.info("############# registerServiceNode create path={} success" , path);
            } else {
                LOGGER.info("############# registerServiceNode create basePath={} failure" , path);
                return false;
            }
        }
        return true;
    }

    static class ServiceInfo {

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
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setServiceName("app_name");
        serviceInfo.setHostAndPort(HostAndPort.fromParts("127.0.0.2" , 10001));
        serviceInfo.setPid(12345);
        serviceInfo.setRegisterTime(new Date());
        ZkServiceRegister zkServiceRegister = new ZkServiceRegister("/zk/test" , serviceInfo);
        zkServiceRegister.registerServiceWithRetryForever(500);
        Thread.sleep(10000000);
    }

}
