package com.xiaogch.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.RetryForever;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.netty.util.internal.ConcurrentHashMap;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
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

    static Logger LOGGER = LogManager.getLogger(ZkServiceDiscover.class);

    private CuratorFramework zkTools;

    private ConcurrentHashMap<String , List<ZkChildrenWatch>> metaMap = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String , PathChildrenCache> pathChildrenCacheMap = new ConcurrentHashMap<>();

    private ExecutorService threadPool = Executors.newFixedThreadPool(1);

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
        threadPool.submit(()->LOGGER.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"));
    }

    public synchronized void registerWatch(String path , ZkChildrenWatch zkChildrenWatch) {
        if (!pathChildrenCacheMap.contains(path)) {
            PathChildrenCache pathChildrenCache = new PathChildrenCache(zkTools , path , true);
            try {
                pathChildrenCache.start(PathChildrenCache.StartMode.NORMAL);
                pathChildrenCache.getListenable().addListener((client , event)->{
                    switch (event.getType()) {
                        case CHILD_ADDED:
                            LOGGER.info("############# path={} CHILD_ADDED" , path+"/" + event.getData().getPath());
                            if (metaMap.get(path) != null) {
                                metaMap.get(path).forEach(watch -> {
                                    if (watch != null) {
                                        watch.onChildAdd(client, path, event);
                                    }
                                });
                            }
                            break;
                        case CHILD_REMOVED:
                            LOGGER.info("############# path={} CHILD_REMOVED " , path+"/" + event.getData().getPath());
                            if (metaMap.get(path) != null) {
                                metaMap.get(path).forEach(watch -> {
                                    if (watch != null) {
                                        watch.onChildRemove(client, path, event);
                                    }
                                });
                            }
                            break;
                        case CHILD_UPDATED:
                            LOGGER.info("############# path={} CHILD_UPDATED" , path+"/" + event.getData().getPath());
                            if (metaMap.get(path) != null) {
                                metaMap.get(path).forEach(watch -> {
                                    if (watch != null) {
                                        watch.onChildUpdate(client, path, event);
                                    }
                                });
                            }
                            break;
                        case CONNECTION_LOST:
                            LOGGER.info("############# event={}" , event);
                            if (metaMap.get(path) != null) {
                                metaMap.get(path).forEach(watch -> {
                                    if (watch != null) {
                                        watch.onLostConnect(client, path, event);
                                    }
                                });
                            }
                            break;
                        case CONNECTION_RECONNECTED:
                            LOGGER.info("############# event={}", event);
                            if (metaMap.get(path) != null) {
                                metaMap.get(path).forEach(watch -> {
                                    if (watch != null){
                                        watch.onReconnected(client , path , event);
                                    }
                                });
                            }
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

        if (metaMap.contains(path)) {
            if (!metaMap.get(path).contains(zkChildrenWatch)) {
                metaMap.get(path).add(zkChildrenWatch);
            }
        } else {
            List<ZkChildrenWatch> watches = new ArrayList<>();
            watches.add(zkChildrenWatch);
            metaMap.put(path , watches);
        }
    }

    public static void main(String[] args) {
        ZkServiceDiscover zkServiceDiscover = new ZkServiceDiscover("127.0.0.1:2181");
        ZkServiceMetaManager serviceMetaManager = new ZkServiceMetaManager();
        zkServiceDiscover.registerWatch("/zk/test/app_name" , serviceMetaManager);
    }


}
