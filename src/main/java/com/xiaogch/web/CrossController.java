package com.xiaogch.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/5/30 14:30 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
@RestController
@RequestMapping("cross")
public class CrossController {

    @RequestMapping(value = "api" , method = RequestMethod.POST)
    @CrossOrigin(value = {"*"}) //允许跨域
    public Object api() {
        return new HashMap<>();
    }
}
