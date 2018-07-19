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

    private CuratorFramework curatorFramework;

    ExecutorService threadPool = Executors.newFixedThreadPool(1);

    private ConcurrentHashMap<String , List<ZkChildrenWatch>> metaMap = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String , PathChildrenCache> pathChildrenCacheMap = new ConcurrentHashMap<>();

    public ZkServiceDiscover(String connectUrl){
        RetryPolicy retryPolicy = new RetryForever(1000);
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(connectUrl)
                .connectionTimeoutMs(30*1000)
                .sessionTimeoutMs(30*1000)
                .retryPolicy(retryPolicy)
                .build();
        LOGGER.info("############# start zookeeper client connectUrl={} begin." , connectUrl);
        curatorFramework.start();
        LOGGER.info("############# start zookeeper client connectUrl={} end.", connectUrl);
    }

    public synchronized void registerWatch(String path , ZkChildrenWatch zkChildrenWatch) {
        if (!pathChildrenCacheMap.contains(path)) {
            PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework , path , true);
            try {
                pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
                pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
                    @Override
                    public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                        switch (event.getType()) {
                            case CHILD_ADDED:
                                metaMap.get(path).forEach(watch -> {
                                    watch.onChildAdd(client , path , event);
                                });
                                LOGGER.info("############# path={} CHILD_ADDED" , path+"/" + event.getData().getPath());
                                break;
                            case CHILD_REMOVED:
                                metaMap.get(path).forEach(watch -> {
                                    watch.onChildRemove(client , path , event);
                                });
                                LOGGER.info("############# path={} CHILD_REMOVED " , path+"/" + event.getData().getPath());
                                break;
                            case CHILD_UPDATED:
                                metaMap.get(path).forEach(watch -> {
                                    watch.onChildUpdate(client , path , event);
                                });
                                LOGGER.info("############# path={} CHILD_UPDATED" , path+"/" + event.getData().getPath());
                                break;
                            case CONNECTION_LOST:
                                metaMap.get(path).forEach(watch -> {
                                    watch.onLostConnect(client , path , event);
                                });
                                LOGGER.info("############# event={}" , event);
                                break;
                            case CONNECTION_RECONNECTED:
                                metaMap.get(path).forEach(watch -> {
                                    watch.onReconnected(client , path , event);
                                });
                                LOGGER.info("############# event={}", event);
                                break;
                            case CONNECTION_SUSPENDED:
                                LOGGER.info("############# event={}", event);
                                break;
                            default:
                                LOGGER.info("############# unkown event={}", event);
                                break;
                        }

                    }
                } , threadPool);
            } catch (Exception e) {
                LOGGER.error("");
            }
            pathChildrenCacheMap.put(path , pathChildrenCache);
        }

        if (metaMap.contains(path)) {
            metaMap.get(path).add(zkChildrenWatch);
        } else {
            List<ZkChildrenWatch>  watches = new ArrayList<>();
            watches.add(zkChildrenWatch);
            metaMap.put(path , watches);
        }
        return;


    }

    public static void main(String[] args) {
        ZkServiceDiscover zkServiceDiscover = new ZkServiceDiscover("127.0.0.1:2181");
        zkServiceDiscover.registerWatch("/zk/test/app_name" , null);
        while (true){

        }

    }


}
