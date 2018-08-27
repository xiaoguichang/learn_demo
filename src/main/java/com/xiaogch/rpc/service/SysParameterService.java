package com.xiaogch.rpc.service;

import com.xiaogch.entity.SysParameterEntity;
import com.xiaogch.rpc.annotation.RpcMethod;
import com.xiaogch.rpc.annotation.RpcService;

import java.util.List;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/27 20:39 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
@RpcService
public interface SysParameterService {

    @RpcMethod
    SysParameterEntity getSysParameter(String code);


    @RpcMethod
    List<SysParameterEntity> getSysParameterList();
}
