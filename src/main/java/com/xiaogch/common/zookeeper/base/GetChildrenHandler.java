package com.xiaogch.common.zookeeper.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooKeeper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by Administrator on 2018/2/11 0011.
 */
public class GetChildrenHandler extends WatchedEventHandler {

    private ConcurrentHashMap<String , String> oldChildren = new ConcurrentHashMap<>();

    private List<String> children = new ArrayList<>();

    static Logger logger = LogManager.getLogger(GetChildrenHandler.class);

    public GetChildrenHandler(ZooKeeper zooKeeper , String path) {
        this.zooKeeper = zooKeeper;
        this.path = path;
        getChildrenFromZookeeper();
    }

    /**
     * 1. 当path 对应的节点被删除时，收到响应的事件
     * 2. 会收到该事件的方法有 getChildren
     */
    @Override
    public synchronized void nodeDeletedProcess() {

        logger.info("nodeDeleted Process");
    }

    @Override
    public void nodeCreatedProcess() {
        logger.info("nodeCreated Process");
    }

    @Override
    public void noneProcess() {
        logger.info("none Process");
    }

    @Override
    public void nodeDataChangedProcess() {
        logger.info("nodeDataChanged Process");
    }

    /***
     * 获取子节点
     * @return
     */
    public synchronized void getChildrenFromZookeeper() {
        try {
            oldChildren.clear();

            List<String> list = zooKeeper.getChildren(path, (WatchedEvent event) -> {
                this.process(event);
            });
            children.clear();
            children.addAll(list);
            list.forEach((item) -> {
                logger.info("old child {}" , item);
                oldChildren.put(item , "ok");
            });
        } catch (Exception e) {
            exceptionHandle(e);
        }
    }


    /**
     * 当path对应的节点的子节点有增加或删除时，收到nodeChildrenChanged事件
     */
    public synchronized void nodeChildrenChangedProcess() {
        logger.info("nodeChildrenChanged process");
        try {
            List<String> list = zooKeeper.getChildren(path ,false);
            children.clear();
            children.addAll(list);
            Map<String , String> newChildren = new HashMap<>();
            list.forEach((item) -> {
                newChildren.put(item , "ok");
                if (!oldChildren.containsKey(item)) {
                    // todo handle for add node
                    logger.info("add node {}" , item);
                    oldChildren.put(item , "ok");
                }
            });
            oldChildren.forEach((key , value) -> {
                if (!newChildren.containsKey(key)) {
                    // todo handle for delete node
                    logger.info("delete node {}" , key);
                    oldChildren.remove(key);
                }
            });
        } catch (Exception e) {
            exceptionHandle(e);
        }

    }

    @Override
    public synchronized void registerWatcher() {
        try {
            zooKeeper.getChildren(path, (WatchedEvent event) -> {
                this.process(event);
            });
        } catch (Exception e) {
            exceptionHandle(e);
        }
    }

    public List<String> getChildren() {
        List<String> result = new ArrayList<>();
        result.addAll(children);
        return result;
    }

    @Override
    public synchronized void clear() {
        children.clear();
        oldChildren.clear();
    }
}
