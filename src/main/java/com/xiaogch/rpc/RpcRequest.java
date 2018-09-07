package com.xiaogch.rpc;

import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/16 15:11 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class RpcRequest {

    private Long requestId;
    private String serviceClassName;
    private String methodName;

    private Class[] parameterTypes;
    private Map<String , String> attributes = new HashMap<>();
    private Map<Integer , Object> parameterMaps = new HashMap<>();


    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getServiceClassName() {
        return serviceClassName;
    }

    public void setServiceClassName(String serviceClassName) {
        this.serviceClassName = serviceClassName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        if (parameterTypes == null) {
            return new Object[0];
        }
        Object[] parameters = new Object[parameterTypes.length];
        if (parameterMaps != null) {
            for(int index = 0 ; index < parameterTypes.length ; index ++) {
                parameters[index] = parameterMaps.get(index);
            }
        }
        return parameters;
    }

    public void setParameterTypesAndValues(Class[] types , Object[] values) {
        Assert.notNull(types , "types can't be null");
        Assert.notNull(values , "values can't be null");
        Assert.isTrue(types.length == values.length , "the length of parameterTypes must equal the length of values");
        parameterMaps.clear();
        this.parameterTypes = types;
        for (int i = 0; i < types.length; i++) {
            parameterMaps.put(i , values[i]);
        }
    }


    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        if (attributes != null) {
            this.attributes = attributes;
        } else {
            this.attributes.clear();
        }
    }

    private String getAttribute(String attributeName) {
        return attributes.get(attributeName);
    }

    private String setAttribute(String attributeName , String attributeValue) {
        return attributes.put(attributeName , attributeValue);
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }
}
