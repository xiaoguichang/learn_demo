package com.xiaogch.rpc.service;

import com.xiaogch.rpc.annotation.RpcMethod;
import com.xiaogch.rpc.annotation.RpcService;

import java.util.Date;
import java.util.List;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/15 17:00 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
@RpcService
public interface HelloService {

    @RpcMethod
    String sayHello();

    @RpcMethod
    String sayHelloByName(String name , Integer age , Date birthDay , List<String> addresses);

    @RpcMethod
    Integer getAge(String name);
}
