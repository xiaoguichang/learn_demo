package com.xiaogch.dao;

import com.xiaogch.entity.UserInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Administrator on 2018/1/29 0029.
 */
@Mapper
public interface UserInfoMapper {

    @Select(value = "select * from t_user_info where id = #{id}")
    UserInfoEntity get(@Param("id") Integer id);
}
