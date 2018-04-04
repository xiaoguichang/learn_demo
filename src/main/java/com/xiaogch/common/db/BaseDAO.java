package com.xiaogch.common.db;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.base.BaseDeleteMapper;
import tk.mybatis.mapper.common.base.BaseInsertMapper;
import tk.mybatis.mapper.common.base.BaseSelectMapper;
import tk.mybatis.mapper.common.base.BaseUpdateMapper;

import java.io.Serializable;

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
public interface BaseDAO<T , PK extends Serializable> extends
        BaseSelectMapper<T> , BaseUpdateMapper<T> ,
        BaseInsertMapper<T> , BaseDeleteMapper<T> ,
        MySqlMapper<T> , Mapper<T> {

}
