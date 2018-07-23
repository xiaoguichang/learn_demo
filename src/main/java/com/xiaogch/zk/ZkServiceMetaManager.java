package com.xiaogch.zk;

import com.alibaba.fastjson.JSONObject;
import com.xiaogch.zk.enums.ServiceEnum;
import com.xiaogch.zk.enums.ServiceMode;
import com.xiaogch.zk.enums.ServiceType;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.util.*;
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

    private final ConcurrentHashMap<ServiceEnum , Map<ServiceMode , List<ServiceInfo>>> serviceMeta = new ConcurrentHashMap<>();

    private Logger LOGGER = LogManager.getLogger(ZkServiceMetaManager.class);

    @Override
    public void onRegister(CuratorFramework framework, String basePath) {

    }

    @Override
    public void onChildAdd(CuratorFramework framework, String basePath, PathChildrenCacheEvent event, PathChildrenCache pathChildrenCache) {
        LOGGER.info("onChildAdd path={} , event={}" , event);
        try {
            updateService(framework , basePath , event , pathChildrenCache);
        } catch (Exception e) {
            LOGGER.error("system not support utf-8 exception: \\n" + e.getMessage()  , e);
        }
    }

    @Override
    public void onChildRemove(CuratorFramework framework, String basePath, PathChildrenCacheEvent event, PathChildrenCache pathChildrenCache) {
        LOGGER.info("onChildRemove path={} , event={}" , event);
        try {
            updateService(framework , basePath , event , pathChildrenCache);
        } catch (Exception e) {
            LOGGER.error("system not support utf-8 exception: \\n" + e.getMessage()  , e);
        }
    }

    @Override
    public void onChildUpdate(CuratorFramework framework, String basePath, PathChildrenCacheEvent event, PathChildrenCache pathChildrenCache) {
        LOGGER.info("onChildUpdate path={} , event={}" , event);
        try {
            updateService(framework , basePath , event , pathChildrenCache);
        } catch (Exception e) {
            LOGGER.error("system not support utf-8 exception: \\n" + e.getMessage()  , e);
        }
    }

    @Override
    public void onLostConnect(CuratorFramework framework, String basePath, PathChildrenCacheEvent event, PathChildrenCache pathChildrenCache) {
        LOGGER.info("onLostConnect path={} , event={}" , event);
    }

    @Override
    public void onReconnected(CuratorFramework framework, String basePath, PathChildrenCacheEvent event, PathChildrenCache pathChildrenCache) {
        LOGGER.info("onReconnected path={} , event={}" , event);
    }

    private void updateService(CuratorFramework framework, String basePath, PathChildrenCacheEvent event , PathChildrenCache pathChildrenCache) throws Exception {

        String data = new String(event.getData().getData() , "utf-8");
        ServiceInfo serviceInfo = JSONObject.parseObject(data , ServiceInfo.class);
        ServiceEnum serviceEnum = ServiceEnum.getServiceEnum(getServiceCode(basePath));
        if (ServiceEnum.UNKNOWN == serviceEnum) {
            return;
        }

        Map<ServiceMode , List<ServiceInfo>> map = new HashMap<>();
        map.put(ServiceMode.GRAY , new ArrayList<>());
        map.put(ServiceMode.WHITE , new ArrayList<>());
        List<ChildData> childDataList = pathChildrenCache.getCurrentData();
        if (childDataList != null && !childDataList.isEmpty()) {
            for (ChildData childData : childDataList) {
                String tempData = new String(childData.getData() , "utf-8");
                ServiceInfo tempServiceInfo  = JSONObject.parseObject(tempData , ServiceInfo.class);
                ServiceEnum tempServiceEnum = ServiceEnum.getServiceEnum(tempServiceInfo.getServiceCode());
                if (ServiceEnum.UNKNOWN == serviceEnum || serviceEnum != tempServiceEnum) {
                    continue;
                }

                if (ServiceMode.GRAY == serviceInfo.getServiceMode()) {
                    map.get(ServiceMode.GRAY).add(serviceInfo);
                }

                if (ServiceMode.WHITE == serviceInfo.getServiceMode()) {
                    map.get(ServiceMode.WHITE).add(serviceInfo);
                }
            }
        }
        serviceMeta.put(serviceEnum  , map);
    }

    public List<ServiceInfo> getServiceInfoList(ServiceEnum serviceEnum , ServiceMode serviceMode){
        Assert.notNull(serviceEnum , "serviceEnum must be not null");
        Assert.notNull(serviceMode , "serviceMode must be not null");
        if (!serviceMeta.containsKey(serviceEnum)) {
            return Collections.emptyList();
        }

        return serviceMeta.get(serviceEnum).get(serviceMode);
    }


    private String getServiceCode(String basePath){
        Assert.notNull(basePath , "basePath must be not null");
        if (basePath.indexOf("/") != -1) {
            return basePath.substring(basePath.lastIndexOf("/"));
        }
        return basePath;
    }

    public void loadServiceInfo(String basePath , CuratorFramework framework) {
        ServiceEnum serviceEnum = ServiceEnum.getServiceEnum(getServiceCode(basePath));
        if (ServiceEnum.UNKNOWN == serviceEnum) {
            return;
        }
        Map<ServiceMode , List<ServiceInfo>> map = new HashMap<>();
        map.put(ServiceMode.GRAY , new ArrayList<>());
        map.put(ServiceMode.WHITE , new ArrayList<>());
        try {
            framework.getChildren().forPath(basePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        serviceMeta.put(serviceEnum , map);
    }

}
