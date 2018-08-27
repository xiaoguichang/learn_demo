package com.xiaogch;

import com.xiaogch.rpc.server.AbstractRpcServer;
import com.xiaogch.rpc.meta.ServiceEnum;
import com.xiaogch.rpc.meta.ServiceEnv;
import com.xiaogch.rpc.meta.ServiceMode;
import com.xiaogch.rpc.meta.ServiceType;
import org.springframework.stereotype.Component;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/16 18:35 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
@Component
public class MyRpcServer extends AbstractRpcServer {

    @Override
    public ServiceMode getServiceMode() {
        return ServiceMode.GRAY;
    }

    @Override
    public ServiceType getServiceType() {
        return ServiceType.RPC;
    }

    @Override
    public ServiceEnv getServiceEnv() {
        return ServiceEnv.DEVP;
    }

    @Override
    public ServiceEnum getServiceEnum() {
        return ServiceEnum.TEST;
    }
}
