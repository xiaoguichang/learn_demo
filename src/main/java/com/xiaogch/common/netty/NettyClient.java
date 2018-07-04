package com.xiaogch.common.netty;

import org.springframework.web.context.ContextLoaderListener;

import java.io.Serializable;

public class NettyClient implements Serializable {


    ContextLoaderListener contextLoaderListener;
    org.springframework.web.context.support.XmlWebApplicationContext applicationContext;


}
