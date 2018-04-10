package com.xiaogch.common.redis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/4/4 19:13 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
@Documented
@Target(ElementType.METHOD)
public @interface RedisCached {

    String key() default "";

    String keyPrefix() default "";

    int expired() default 24*60*60;

}
