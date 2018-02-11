package com.xiaogch.web;

import com.xiaogch.dao.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/1/29 0029.
 */

@RestController
@RequestMapping("user")
public class UserInfoController extends BaseController {

    @Autowired
    UserInfoMapper userInfoMapper;

    @RequestMapping("/get/{id}")
    public Object get(@PathVariable Integer id){
        return userInfoMapper.get(id);
    }

}
