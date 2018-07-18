package com.xiaogch.guava;

import com.google.common.util.concurrent.RateLimiter;
import com.xiaogch.entity.UserInfoEntity;

import java.util.Objects;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/18 16:30 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class ObjectsTest {

    public static void main(String[] args) {
//        UserInfoEntity userInfoEntity = new UserInfoEntity();
//        System.out.println(Objects.toString(userInfoEntity));
        RateLimiter rateLimiter = RateLimiter.create(100);

    }
}
