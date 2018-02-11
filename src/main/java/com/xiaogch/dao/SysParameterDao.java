package com.xiaogch.dao;

import com.xiaogch.entity.SysParameterEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/1/31 0031.
 */
@Repository
public interface SysParameterDao {

    @Select("select id , value , code , create_time as createTime from t_sys_parameter where code = #{code}")
    SysParameterEntity selectByCode(@Param("code") String code);

    @Insert("insert into t_sys_parameter (code , value) values(#{code}, #{value})")
    int insert(SysParameterEntity entity);

    @Delete("delete from t_sys_parameter where code = #{code}")
    int deleteByCode(@Param("code") String code);

    @Update("update t_sys_parameter set value = #{value} where code = #{code}")
    int updateValueByCode(@Param("code") String code , @Param("value") String value);

    @Select("select * from t_sys_parameter limit #{offset} , #{size}")
    List<SysParameterEntity> selectList(@Param("offset") Integer offset , @Param("size") Integer size);

}
