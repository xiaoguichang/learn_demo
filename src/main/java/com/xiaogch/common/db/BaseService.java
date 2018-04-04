package com.xiaogch.common.db;

import java.util.List;
/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/4/4 18:05 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public interface BaseService<T> {

    int insert(T t);

    int insertSelective(T t);

    int insertList(List<T> list);

    List<T> selectAll();

    T selectByPrimaryKey(Object key);

    List<T> selectList(T t);

    int updateByPrimaryKey(T t);

    int updateByPrimaryKeySelective(T t);

    int deleteByPrimaryKey(Object key);
}
