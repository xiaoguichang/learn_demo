package com.xiaogch.rpc.annotation;

import java.lang.annotation.*;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/15 16:44 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcService {
}
