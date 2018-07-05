package com.xiaogch.gateway.filter;

import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/5 16:36 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class GatewayFilterManager {

    private static GatewayFilterManager MANAGER = new GatewayFilterManager();
    private final Map<String , GatewayFilter> filterMap = new ConcurrentHashMap<>();
    private final Map<GatewayFilter.FilterType , List<GatewayFilter>> filterListMap= new ConcurrentHashMap<>();

    public static GatewayFilterManager getInstance(){
        return MANAGER;
    }

    public List<GatewayFilter> getFiltersByType(GatewayFilter.FilterType filterType) {
        Assert.notNull(filterType , "filterType can't be null");
        List<GatewayFilter> filters = filterListMap.get(filterType);
        filters = filters == null ? Collections.EMPTY_LIST : filters;
        return filters;
    }

    public synchronized void remove(String filterName) {
        Assert.notNull(filterName , "filter name can't be null");
        if (filterMap.containsKey(filterName)){
            GatewayFilter filter = filterMap.remove(filterName);
            if (filter != null) {
                initFilterListMap();
            }
        }
    }

    public synchronized void regist(String filterName , GatewayFilter filter) {
        Assert.notNull(filter , "filter can't be null");
        Assert.notNull(filter.getFilterType() , "filter's filterType can't be null");
        if (filterName == null){
            filterName = filter.getClass().getName();
        }
        filterMap.put(filterName , filter);
        initFilterListMap();
    }

    private void initFilterListMap() {
        List<GatewayFilter> preFilters = new ArrayList<>();
        List<GatewayFilter> routeFilters = new ArrayList<>();
        List<GatewayFilter> postFilters = new ArrayList<>();
        List<GatewayFilter> errorFilters = new ArrayList<>();
        filterMap.forEach((key , filter) ->{
            switch (filter.getFilterType()) {
                case PRE:
                    preFilters.add(filter);
                    break;
                case ROUTE:
                    routeFilters.add(filter);
                    break;
                case POST:
                    postFilters.add(filter);
                    break;
                case ERROR:
                    errorFilters.add(filter);
                    break;
            }
        });

        preFilters.sort(Comparator.comparingInt(GatewayFilter::getFilterOrder));
        routeFilters.sort(Comparator.comparingInt(GatewayFilter::getFilterOrder));
        postFilters.sort(Comparator.comparingInt(GatewayFilter::getFilterOrder));
        errorFilters.sort(Comparator.comparingInt(GatewayFilter::getFilterOrder));

        filterListMap.put(GatewayFilter.FilterType.PRE , preFilters);
        filterListMap.put(GatewayFilter.FilterType.ROUTE , routeFilters);
        filterListMap.put(GatewayFilter.FilterType.POST , postFilters);
        filterListMap.put(GatewayFilter.FilterType.ERROR , errorFilters);
    }
}
