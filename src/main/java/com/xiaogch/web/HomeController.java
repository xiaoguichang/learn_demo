package com.xiaogch.web;

import com.xiaogch.common.http.response.Response;
import com.xiaogch.common.redis.annotation.RedisDatasource;
import com.xiaogch.system.Systems;
import com.xiaogch.wechat.common.service.FormIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * ProjectName: springboot<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: springboot <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/1/29 13:39 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
@RestController
public class HomeController extends BaseController {

    @Autowired
    FormIdService formIdService;

    @RequestMapping("/")
    public Object home() {
        Map<String  , Object> data = new HashMap<>();
        data.put("welcomeMessage" , "hello , welcome to spring boot world");
        return Response.buildSuccessRsp(data);
    }

    @RequestMapping("/master/commitFormId")
    @RedisDatasource("defaultRedisDatasource")
    public Object commitFormId(Integer userId , String formId) {
        formIdService.saveFromIdToRedis(Systems.TEST  , formId , userId ,  System.currentTimeMillis());
        return Response.buildSuccessRsp();
    }

    @RequestMapping("/slave/commitFormId")
    @RedisDatasource("slaveRedisDatasource")
    public Object commitFormId(int userId , String formId) {
        formIdService.saveFromIdToRedis(Systems.TEST  , formId , userId ,  System.currentTimeMillis());
        return Response.buildSuccessRsp();
    }


}
