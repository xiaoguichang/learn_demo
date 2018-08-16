package com.xiaogch.rpc;

import com.xiaogch.rpc.meta.HostAndPort;

import java.lang.reflect.Proxy;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/16 18:46 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class RpcClient {

    public static <T> T getInstance(HostAndPort hostAndPort , T t) {

        return (T) Proxy.newProxyInstance(t.getClass().getClassLoader() ,
                new Class[]{t.getClass()} , new RpcInvocationHandler(hostAndPort));
    }



}

