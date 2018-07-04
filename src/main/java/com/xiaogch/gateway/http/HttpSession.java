package com.xiaogch.gateway.http;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2018/7/4 0004.
 */
public class HttpSession {

    private long createTime;
    private String sessionId;
    private long lastAccessedTime;
    private Map<String , Object> attributes = new ConcurrentHashMap<>();
    private int maxInactiveInterval = 3600;


    public HttpSession(){

    }

    public HttpSession(String sessionId) {
        this.sessionId = sessionId;
        this.createTime = System.currentTimeMillis();
    }

    public long getCreationTime() {
        return createTime;
    }

    public String getId() {
        return sessionId;
    }


    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    public void setMaxInactiveInterval(int maxInactiveInterval) {
        maxInactiveInterval = maxInactiveInterval;
    }

    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }


    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public Set<String> getAttributeNames() {
        return attributes.keySet();
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name , value);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public void invalidate() {

    }

    public boolean isNew() {
        return false;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setLastAccessedTime(long lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }
}
