package com.xiaogch.common.db.impl;

import com.xiaogch.common.db.BaseDAO;
import com.xiaogch.common.db.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/4/4 18:20 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */


public abstract class BaseServiceImpl<D extends BaseDAO<PK , T>, T, PK extends Serializable> implements BaseService<D, T, PK> {

    @Autowired
    private BaseDAO baseDAO;

    public BaseDAO getBaseDAO() {
        return baseDAO;
    }

    public void setBaseDAO(BaseDAO baseDAO) {
        this.baseDAO = baseDAO;
    }

    public int insert(T t) {
        return baseDAO.insert(t);
    }

    public int insertSelective(T t) {
        return baseDAO.insertSelective(t);
    }

    public int insertList(List<T> list) {
        return baseDAO.insertList(list);
    }

    public List<T> selectList(T t) {
        return baseDAO.select(t);
    }

    public T selectByPrimaryKey(Object key) {
        return (T) baseDAO.selectByPrimaryKey(key);
    }

    public List<T> selectAll() {
        return baseDAO.selectAll();
    }

    public int updateByPrimaryKey(T t) {
        return baseDAO.updateByPrimaryKey(t);
    }

    public int updateByPrimaryKeySelective(T t) {
        return baseDAO.updateByPrimaryKeySelective(t);
    }

    public int deleteByPrimaryKey(Object key) {
        return baseDAO.deleteByPrimaryKey(key);
    }

}
