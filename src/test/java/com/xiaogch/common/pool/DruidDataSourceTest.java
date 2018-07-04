package com.xiaogch.common.pool;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.SQLException;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/4 11:24 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class DruidDataSourceTest {

    public static void main(String[] args) throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.getConnection();
    }
}
