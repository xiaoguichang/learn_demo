package com.xiaogch.gateway.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/5 18:51 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class MyPreFilter2 implements GatewayFilter {
    private static Logger LOGGER = LogManager.getLogger(MyPreFilter2.class);
    @Override
    public int getFilterOrder() {
        return 0;
    }

    @Override
    public Object doFilter() {
        LOGGER.info("MyPreFilter2 doFilter running ....");
        return null;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.PRE;
    }
}
