package com.xiaogch.gateway.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/5 16:57 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class GatewayRunner {

    private static Logger LOGGER = LogManager.getLogger(GatewayRunner.class);

    public void run() {
        try {
            runPreFilter();
        } catch (Exception e) {
            error(e);
            runPostFilter();
            LOGGER.error(e.getMessage() , e);
            return;
        }

        try {
            runRouteFilter();
        } catch (Exception e) {
            error(e);
            runPostFilter();
            LOGGER.error(e.getMessage() , e);
            return;
        }

        try {
            runPostFilter();
        } catch (Exception e) {
            error(e);
            LOGGER.error(e.getMessage() , e);
        }
    }

    private void error(Throwable throwable) {

    }

    private void runPreFilter(){
        runFilterByType(GatewayFilter.FilterType.PRE);
    }

    private void runPostFilter(){
        runFilterByType(GatewayFilter.FilterType.POST);
    }

    private void runRouteFilter(){
        runFilterByType(GatewayFilter.FilterType.ROUTE);
    }

    private void runErrorFilter(){
        runFilterByType(GatewayFilter.FilterType.ERROR);
    }

    private void runFilterByType(GatewayFilter.FilterType filterType) {
        List<GatewayFilter> filters = GatewayFilterManager.getInstance().getFiltersByType(filterType);
        int index = 0;
        filters.forEach(filter -> {
            LOGGER.info("{} filter[{}] filterClass={}" , filterType , index , filter.getClass());
            filter.doFilter();
        });
    }
}
