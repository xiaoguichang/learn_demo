package com.xiaogch.gateway.filter;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/5 18:32 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public abstract class BasePreFilter implements GatewayFilter {

    @Override
    public FilterType getFilterType() {
        return null;
    }

}
