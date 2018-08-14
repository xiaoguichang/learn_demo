package com.xiaogch.wechat.common.service.impl;

import com.xiaogch.common.redis.standalone.RedisListService;
import com.xiaogch.system.Systems;
import com.xiaogch.wechat.common.dto.FormIdDTO;
import com.xiaogch.wechat.common.service.FormIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

/**
 * ProjectName: gfw-parent<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: gfw-parent <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/4/13 15:44 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
@Service
public class FormIdServiceImpl implements FormIdService {

   @Autowired
   private RedisListService redisListService;

   /**
    * 保存formId到redisList 结构
    * @param systems 具体应用
    * @param formId 微信formId
    * @param userId 用户id
    * @param createTimestamp formId 创建时间
    * @return
    */

   public boolean saveFromIdToRedis(Systems systems , String formId, Integer userId , long createTimestamp) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      long currentTime = System.currentTimeMillis();
      long expiredTime = currentTime + 7*24*60*60*1000;
      FormIdDTO formIdDTO = new FormIdDTO();
      formIdDTO.setFromId(formId);
      formIdDTO.setCreateTime(currentTime);
      formIdDTO.setCreateTimeStr(sdf.format(currentTime));
      formIdDTO.setExpiredTime(expiredTime);
      formIdDTO.setExpiredTimeStr(sdf.format(expiredTime));
      String key = getRedisKeyForUserFormidList(systems , userId);
      return redisListService.rpush(key , formIdDTO , 7*24*60*60 - 60); // 提前一分钟失效
   }

   /**
    * 从redisList结构获取有效的formId，过滤已失效的
    * @param systems 具体应用
    * @param userId 用户id
    *
    * @return
    */
   @Override
   public String getFormIdFromRedis(Systems systems , Integer userId) {
      String key = getRedisKeyForUserFormidList(systems , userId);
      FormIdDTO formIdDTO = redisListService.lpop(key , FormIdDTO.class);
      while (true) {
         if (formIdDTO == null) {
            return null;
         }
         if (formIdDTO.getExpiredTime() > System.currentTimeMillis()) {
            return formIdDTO.getFromId();
         }
         formIdDTO = redisListService.lpop(key , FormIdDTO.class);
      }
   }

   /**
    * 获取存储formId的redis Key
    * @param systems 具体应用
    * @param userId 用户id
    * @return
    */
   private String getRedisKeyForUserFormidList(Systems systems , Integer userId) {
      StringBuilder sb = new StringBuilder(systems.getCode());
      sb.append(":user:formIdList:").append(userId);
      return sb.toString();
   }
}
