package com.xiaogch.rpc;

import com.xiaogch.entity.SysParameterEntity;
import com.xiaogch.rpc.client.RpcServiceClient;
import com.xiaogch.rpc.meta.HostAndPort;
import com.xiaogch.rpc.service.HelloService;
import com.xiaogch.rpc.service.SysParameterService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/17 17:34 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class RpcTest {

    public static void main(String[] args) {
        try {
            Logger logger = LogManager.getLogger(RpcTest.class);
            HostAndPort hostAndPort = new HostAndPort("127.0.0.1" , 10001);
            HelloService helloService = RpcServiceClient.getInstance().getService(hostAndPort , HelloService.class);
            String result = helloService.sayHello();
            logger.info("helloService.sayHello() result is {}" ,  result);

            result = helloService.sayHelloByName("肖贵长");
            logger.info("helloService.sayHelloByName() result is {}" ,  result);

            result = helloService.sayHello();
            logger.info("helloService.sayHello() result is {}" ,  result);

            result = helloService.sayHelloByName("肖贵长");
            logger.info("helloService.sayHelloByName() result is {}" ,  result);

            SysParameterService sysParameterService = RpcServiceClient.getInstance().getService(hostAndPort , SysParameterService.class);
            SysParameterEntity entity = sysParameterService.getSysParameter("code a");
            logger.info("sysParameterService.getSysParameter() result is {}" ,  entity);

            List<SysParameterEntity> entityList = sysParameterService.getSysParameterList();
            logger.info("sysParameterService.getSysParameterList() result is {}" ,  entityList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

//class A implements InvocationHandler {
//
//    @Override
//    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        System.out.println("888888888888888888");
//        return null;
//    }
//}
