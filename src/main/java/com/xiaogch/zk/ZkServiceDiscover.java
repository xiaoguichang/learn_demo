package com.xiaogch.zk;

import com.alibaba.fastjson.JSONObject;
import com.xiaogch.zk.enums.ServiceEnum;
import com.xiaogch.zk.enums.ServiceMode;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.retry.RetryForever;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.netty.util.internal.ConcurrentHashMap;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/19 14:36 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class ZkServiceDiscover {

    private static String zkBasePath = ZkConstants.ZK_SERVICE_BASE_PATH;

    static Logger LOGGER = LogManager.getLogger(ZkServiceDiscover.class);

    private final ConcurrentHashMap<ServiceEnum , Map<ServiceMode , List<ServiceInfo>>> serviceMeta = new ConcurrentHashMap<>();

    /** zookeeper 连接工具*/
    private CuratorFramework zkTools;

    /** 路径-PathChildrenCache映射关系 */
    private final ConcurrentHashMap<String , PathChildrenCache> pathChildrenCacheMap = new ConcurrentHashMap<>();

    private final ExecutorService threadPool = Executors.newFixedThreadPool(1);


    public ZkServiceDiscover(String connectUrl){
        zkTools = CuratorFrameworkFactory.builder()
                .connectString(connectUrl)
                .connectionTimeoutMs(30*1000)
                .sessionTimeoutMs(30*1000)
                .retryPolicy(new RetryForever(1000))
                .build();
        LOGGER.info("############# start zookeeper client connectUrl={} begin." , connectUrl);
        zkTools.start();
        LOGGER.info("############# start zookeeper client connectUrl={} end.", connectUrl);

        zkTools.getConnectionStateListenable().addListener((client , newState)-> LOGGER.info("newState={}" , newState));
        // submit a task do nothing , to keep program run forever ...
        threadPool.submit(()->LOGGER.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"));

        // 初始化服务
        for (ServiceEnum serviceEnum : ServiceEnum.values()){
            serviceMeta.put(serviceEnum , loadServiceInfo(serviceEnum));
            LOGGER.info("############# init serviceMeta serviceEnum={} , serviceInfo={}" , serviceEnum , serviceMeta.get(serviceEnum));
        }
    }

    /**
     * 为特定的路径注册一个监听器
     * @param serviceEnum 监听的服务
     */
    public synchronized void registerWatch(ServiceEnum serviceEnum) {
        String path = zkBasePath + "/" + serviceEnum.code;
        if (!pathChildrenCacheMap.contains(path)) {
            PathChildrenCache pathChildrenCache = new PathChildrenCache(zkTools , path , true);
            try {
                pathChildrenCache.start(PathChildrenCache.StartMode.NORMAL);
                pathChildrenCache.getListenable().addListener((client , event)->{
                    switch (event.getType()) {
                        case CHILD_ADDED:
                            LOGGER.info("############# path={} CHILD_ADDED" , path+"/" + event.getData().getPath());
                            LOGGER.info("############# CHILD_ADDED before serviceInfo is {}" , serviceMeta.get(serviceEnum));
                            updateOrAddService(serviceEnum , event);
                            LOGGER.info("############# CHILD_ADDED after serviceInfo is {}" , serviceMeta.get(serviceEnum));
                            break;
                        case CHILD_REMOVED:
                            LOGGER.info("############# path={} CHILD_REMOVED " , path+"/" + event.getData().getPath());
                            LOGGER.info("############# CHILD_REMOVED before serviceInfo is {}" , serviceMeta.get(serviceEnum));
                            deleteService(serviceEnum , event);
                            LOGGER.info("############# CHILD_REMOVED after serviceInfo is {}" , serviceMeta.get(serviceEnum));
                            break;
                        case CHILD_UPDATED:
                            LOGGER.info("############# path={} CHILD_UPDATED" , path+"/" + event.getData().getPath());
                            LOGGER.info("############# CHILD_UPDATED before serviceInfo is {}" , serviceMeta.get(serviceEnum));
                            updateOrAddService(serviceEnum , event);
                            LOGGER.info("############# CHILD_UPDATED after serviceInfo is {}" , serviceMeta.get(serviceEnum));
                            break;
                        case CONNECTION_LOST:
                            LOGGER.info("############# event={}" , event);
                            break;
                        case CONNECTION_RECONNECTED:
                            LOGGER.info("############# event={}", event);
                            break;
                        case CONNECTION_SUSPENDED:
                            LOGGER.info("############# event={}", event);
                            break;
                        default:
                            LOGGER.info("############# unkown event={}", event);
                            break;
                    }
                } , threadPool);
            } catch (Exception e) {
                LOGGER.error(e.getMessage() , e);
            }
            pathChildrenCacheMap.put(path , pathChildrenCache);
        }
    }

    private synchronized void deleteService(ServiceEnum serviceEnum, PathChildrenCacheEvent event) {
        Map<ServiceMode , List<ServiceInfo>> map = new HashMap<>();
        map.put(ServiceMode.GRAY , new ArrayList<>());
        map.put(ServiceMode.WHITE , new ArrayList<>());

        Map<ServiceMode , List<ServiceInfo>> tempMap = serviceMeta.get(serviceEnum);
        tempMap.get(ServiceMode.GRAY).forEach(serviceInfo -> {
            if (!Objects.equals(event.getData().getPath() , getServiceNodePath(serviceInfo))) {
                map.get(ServiceMode.GRAY).add(serviceInfo);
            }
        });

        tempMap.get(ServiceMode.WHITE).forEach(serviceInfo -> {
            if (!Objects.equals(event.getData().getPath() , getServiceNodePath(serviceInfo))) {
                map.get(ServiceMode.WHITE).add(serviceInfo);
            }
        });

        serviceMeta.put(serviceEnum , map);
    }


    /**
     * 更新或添加服务
     * @param serviceEnum
     * @param event
     */
    private synchronized void updateOrAddService(ServiceEnum serviceEnum, PathChildrenCacheEvent event) {

        byte[] data = event.getData().getData();
        String tempData = new String(data);
        LOGGER.info("############# updateOrAddService serviceEnum={} , path={} , childrenData={}" , serviceEnum , event.getData().getPath() , tempData);
        ServiceInfo tempServiceInfo  = JSONObject.parseObject(tempData , ServiceInfo.class);
        ServiceEnum tempServiceEnum = ServiceEnum.getServiceEnum(tempServiceInfo.getServiceCode());
        if (ServiceEnum.UNKNOWN == serviceEnum || serviceEnum != tempServiceEnum) {
            return;
        }

        Map<ServiceMode , List<ServiceInfo>> map = new HashMap<>();
        map.put(ServiceMode.GRAY , new ArrayList<>());
        map.put(ServiceMode.WHITE , new ArrayList<>());

        Map<ServiceMode , List<ServiceInfo>> tempMap = serviceMeta.get(serviceEnum);
        tempMap.get(ServiceMode.GRAY).forEach(serviceInfo -> {
            if (!Objects.equals(event.getData().getPath() , getServiceNodePath(serviceInfo))) {
                map.get(ServiceMode.GRAY).add(serviceInfo);
            }
        });

        tempMap.get(ServiceMode.WHITE).forEach(serviceInfo -> {
            if (!Objects.equals(event.getData().getPath() , getServiceNodePath(serviceInfo))) {
                map.get(ServiceMode.WHITE).add(serviceInfo);
            }
        });

        if (ServiceMode.GRAY == tempServiceInfo.getServiceMode()) {
            map.get(ServiceMode.GRAY).add(tempServiceInfo);
        }

        if (ServiceMode.WHITE == tempServiceInfo.getServiceMode()) {
            map.get(ServiceMode.WHITE).add(tempServiceInfo);
        }

        serviceMeta.put(serviceEnum , map);
    }




    private String getServiceNodeBasePath(ServiceInfo serviceInfo) {
        return zkBasePath + "/" + serviceInfo.getServiceCode();
    }

    private String getServiceNodePath(ServiceInfo serviceInfo) {
        return getServiceNodeBasePath(serviceInfo) + "/" + serviceInfo.getHostAndPort().getHost() + ":"
                + serviceInfo.getHostAndPort().getPort() + "@" + serviceInfo.getPid();
    }

    private Map<ServiceMode , List<ServiceInfo>> loadServiceInfo(ServiceEnum serviceEnum) {
        Map<ServiceMode , List<ServiceInfo>> map = new HashMap<>();
        map.put(ServiceMode.GRAY , new ArrayList<>());
        map.put(ServiceMode.WHITE , new ArrayList<>());
        String path = zkBasePath + "/" + serviceEnum.code;
        List<String> children = null;
        try {
            children = zkTools.getChildren().forPath(path);
            LOGGER.info("############# loadServiceInfo serviceEnum={} , children={}" , serviceEnum , path , children);
        } catch (Exception e) {
            LOGGER.error("############# loadServiceInfo getChildren , path=" + path + " exception: " + e.getMessage() , e);
        }

        if (children != null && !children.isEmpty()) {
            for (String childrenPath : children) {
                childrenPath = path + "/" + childrenPath;
                try {
                    byte[] data = zkTools.getData().forPath(childrenPath);
                    String tempData = new String(data);
                    LOGGER.info("############# loadServiceInfo serviceEnum={} , children={} , childrenData={}" , serviceEnum , childrenPath , tempData);
                    ServiceInfo tempServiceInfo  = JSONObject.parseObject(tempData , ServiceInfo.class);
                    ServiceEnum tempServiceEnum = ServiceEnum.getServiceEnum(tempServiceInfo.getServiceCode());
                    if (ServiceEnum.UNKNOWN == serviceEnum || serviceEnum != tempServiceEnum) {
                        continue;
                    }
                    if (ServiceMode.GRAY == tempServiceInfo.getServiceMode()) {
                        map.get(ServiceMode.GRAY).add(tempServiceInfo);
                    }

                    if (ServiceMode.WHITE == tempServiceInfo.getServiceMode()) {
                        map.get(ServiceMode.WHITE).add(tempServiceInfo);
                    }
                } catch (Exception e) {
                    LOGGER.error("get data and parse data , childrenPath=" + childrenPath + " exception: " + e.getMessage() , e);
                }
            }
        }
        return map;
    }

    public static void main(String[] args) {
        ZkServiceDiscover zkServiceDiscover = new ZkServiceDiscover("127.0.0.1:2181");
        zkServiceDiscover.registerWatch(ServiceEnum.WECHAT);
    }


}
