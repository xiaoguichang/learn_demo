package com.xiaogch.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/20 18:23 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
@Service
public class ZkServiceMetaManager implements ZkChildrenWatch {
    private Logger LOGGER = LogManager.getLogger(ZkServiceMetaManager.class);

    @Override
    public void onChildAdd(CuratorFramework framework, String basePath, PathChildrenCacheEvent event) {
        LOGGER.info("onChildAdd path={} , event={}" , event);
    }

    @Override
    public void onChildRemove(CuratorFramework framework, String basePath, PathChildrenCacheEvent event) {
        LOGGER.info("onChildRemove path={} , event={}" , event);
    }

    @Override
    public void onChildUpdate(CuratorFramework framework, String basePath, PathChildrenCacheEvent event) {
        LOGGER.info("onChildUpdate path={} , event={}" , event);
    }

    @Override
    public void onLostConnect(CuratorFramework framework, String basePath, PathChildrenCacheEvent event) {
        LOGGER.info("onLostConnect path={} , event={}" , event);
    }

    @Override
    public void onReconnected(CuratorFramework framework, String basePath, PathChildrenCacheEvent event) {
        LOGGER.info("onReconnected path={} , event={}" , event);
    }


}
