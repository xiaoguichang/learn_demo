package com.xiaogch.zk;

import com.alibaba.fastjson.JSONObject;
import com.xiaogch.common.util.SystemUtil;
import com.xiaogch.zk.enums.ServiceEnum;
import com.xiaogch.zk.enums.ServiceEnv;
import com.xiaogch.zk.enums.ServiceMode;
import com.xiaogch.zk.enums.ServiceType;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.RetryForever;
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
    private final String basePath = ZkConstants.ZK_SERVICE_BASE_PATH;
    private CuratorFramework zkTools;

    private ExecutorService threadPool = Executors.newFixedThreadPool(1);

    public ZkServiceRegister() {
        RetryPolicy retryPolicy = new RetryForever(1000);
        zkTools = CuratorFrameworkFactory.builder()
                .connectString(connectUrl)
                .connectionTimeoutMs(30*1000)
                .sessionTimeoutMs(30*1000)
                .retryPolicy(retryPolicy)
                .build();
        LOGGER.info("############# start zookeeper client connectUrl={} begin." , connectUrl);
        zkTools.start();
        LOGGER.info("############# start zookeeper client connectUrl={} end.", connectUrl);
    }


    /**
     * 注册服务，如果失败会一直尝试注册，直到注册成功为止
     * @param retryInterval 重试时间间隔
     */
    public void registerServiceWithRetryForever(int retryInterval , ServiceInfo serviceInfo) {
        threadPool.submit(()->{
            while (!createBasePath(serviceInfo)) {
                LOGGER.info("############# registerServiceWithRetryForever createBasePath failure");
                try {
                    Thread.sleep(retryInterval);
                } catch (InterruptedException e) {
                    LOGGER.error(e.getMessage() , e);
                }
            }
            LOGGER.info("############# registerServiceWithRetryForever createBasePath success");
            // 注册服务节点
            while (!registerServiceNode(serviceInfo)) {
                LOGGER.info("############# registerServiceWithRetryForever try registerServiceNode failure");
                try {
                    Thread.sleep(retryInterval);
                } catch (InterruptedException e) {
                    LOGGER.error(e.getMessage() , e);
                }
            }
            LOGGER.info("############# registerServiceWithRetryForever registerServiceNode success");
            // 增加链接状态监听器
            addConnectionStateListener(retryInterval , serviceInfo);
        });

    }


    /**
     * 注册服务，只会注册一次，不会重复尝试
     * @param reconnectRetryInterval 重连重新注册重试时间间隔
     */
    public void registerServiceOnlyOneTime(int reconnectRetryInterval , ServiceInfo serviceInfo){
        if (!createBasePath(serviceInfo)) {
            LOGGER.info("############# registerServiceOnlyOneTime createBasePath failure , stop  register service");
            return;
        }
        boolean registerServiceNodeResult = registerServiceNode(serviceInfo);
        LOGGER.info("############# registerServiceOnlyOneTime registerServiceNode {}", registerServiceNodeResult ? "success" : "failure");
        // 创建成功注册
        if (registerServiceNodeResult) {
            addConnectionStateListener(reconnectRetryInterval , serviceInfo);
        }
    }

    private String getServiceNodeBasePath(ServiceInfo serviceInfo) {
        return basePath + "/" + serviceInfo.getServiceCode();
    }

    private String getServiceNodePath(ServiceInfo serviceInfo) {
        return getServiceNodeBasePath(serviceInfo) + "/" + serviceInfo.getHostAndPort().getHost() + ":"
                + serviceInfo.getHostAndPort().getPort() + "@" + serviceInfo.getPid();
    }

    /**
     * 添加链接状态监听器
     * @param reconnectRetryInterval 重连重新注册重试时间间隔
     */
    private void addConnectionStateListener(int reconnectRetryInterval, ServiceInfo serviceInfo) {
        zkTools.getConnectionStateListenable().addListener((client, newState) -> {
            String serviceNodePath = getServiceNodePath(serviceInfo);
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
                    threadPool.submit(()->{
                        while (!registerServiceNode(serviceInfo)) {
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
    public boolean createBasePath(ServiceInfo serviceInfo) {
        Stat stat = null;
        String path= getServiceNodeBasePath(serviceInfo);
        try {
            stat = zkTools.checkExists().forPath(path);
        } catch (Exception e) {
            LOGGER.error("############# checkExists path=" + path + " exception: " + e.getMessage() , e);
        }
        // 判断basePath 是否已经存在
        LOGGER.info("############# registerService checkExists basePath={} , stat={}" , path , stat);
        if (stat == null) {
            // 创建basePath
            try {
                zkTools.create().creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath(path);
                LOGGER.info("############# registerService create basePath={} success" , path);
            } catch (Exception e) {
                LOGGER.error("############# registerService create basePath=" + path + " exception: " + e.getMessage(), e);
                if (e instanceof KeeperException.NodeExistsException) {
                    LOGGER.info("############# registerService create basePath={} success" , path);
                } else {
                    LOGGER.info("############# registerService create basePath={} failure" , path);
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
    public boolean registerServiceNode(ServiceInfo serviceInfo){
        String path = getServiceNodePath(serviceInfo);
        try {
            String nodeData = JSONObject.toJSONStringWithDateFormat(serviceInfo , "yyyy-MM-dd HH:mm:ss");
            zkTools.create().creatingParentsIfNeeded()
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


    public static void main(String[] args) throws InterruptedException {
        ServiceInfo serviceInfo = new ServiceInfo(ServiceEnum.WECHAT);
        serviceInfo.setHostAndPort(new HostAndPort(SystemUtil.findLocalHostOrFirstNonLoopbackAddress() , SystemUtil.getPid()));
        serviceInfo.setPid(SystemUtil.getPid());
        serviceInfo.setServiceEnv(ServiceEnv.DEVP);
        serviceInfo.setServiceMode(ServiceMode.GRAY);
        serviceInfo.setServiceType(ServiceType.RPC);
        serviceInfo.setStartupTime(new Date());

        ZkServiceRegister zkServiceRegister = new ZkServiceRegister();
        zkServiceRegister.registerServiceWithRetryForever(500 , serviceInfo);
    }

}
