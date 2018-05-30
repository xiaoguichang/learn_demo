package com.xiaogch.web;

import com.xiaogch.common.http.response.Response;
import org.springframework.web.bind.annotation.CrossOrigin;
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

    @RequestMapping("/")
    Object home() {
        Map<String  , Object> data = new HashMap<>();
        data.put("welcomeMessage" , "hello , welcome to spring boot world");
        return new Response(data);
    }
}
