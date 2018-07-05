package com.xiaogch.gateway.filter;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/5 16:31 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public interface GatewayFilter {

    enum FilterType {
        PRE , ROUTE ,ERROR , POST
    }

    FilterType getFilterType();

    int getFilterOrder();

    Object doFilter();
}
