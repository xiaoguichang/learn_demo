package com.xiaogch.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/19 14:44 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public interface ZkChildrenWatch {

    void onRegister(CuratorFramework framework , String basePath);

    void onChildAdd(CuratorFramework framework, String basePath, PathChildrenCacheEvent event, PathChildrenCache pathChildrenCache);

    void onChildRemove(CuratorFramework framework, String basePath, PathChildrenCacheEvent event, PathChildrenCache pathChildrenCache);

    void onChildUpdate(CuratorFramework framework, String basePath, PathChildrenCacheEvent event, PathChildrenCache pathChildrenCache);

    void onLostConnect(CuratorFramework framework, String basePath, PathChildrenCacheEvent event, PathChildrenCache pathChildrenCache);

    void onReconnected(CuratorFramework framework, String basePath, PathChildrenCacheEvent event, PathChildrenCache pathChildrenCache);
}
