package com.xiaogch.wechat.common.service;

import com.xiaogch.system.Systems;

/**
 * ProjectName: gfw-parent<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: gfw-parent <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/4/13 15:59 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public interface FormIdService {

    /**
     * 保存formId到redisList 结构
     * @param formId 微信formId
     * @param systems 具体应用
     * @param userId 用户id
     * @param createTimestamp formId 创建时间
     * @return
     */
    boolean saveFromIdToRedis(Systems systems, String formId, Integer userId, long createTimestamp);

    /**
     * 从redisList结构获取有效的formId，过滤已失效的
     * @param systems 具体应用
     * @param userId 用户id
     * @return
     */
    String getFormIdFromRedis(Systems systems, Integer userId);
}
