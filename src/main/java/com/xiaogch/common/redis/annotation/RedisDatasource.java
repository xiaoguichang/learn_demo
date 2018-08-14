package com.xiaogch.common.redis.annotation;

import java.lang.annotation.*;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/14 17:25 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisDatasource {
    String value() default "defaultRedisDatasource";
}
