package com.xiaogch.rpc.service.impl;

import com.xiaogch.entity.SysParameterEntity;
import com.xiaogch.rpc.service.SysParameterService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/8/27 20:41 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
@Service
public class SysParameterServiceImpl implements SysParameterService {

    @Override
    public SysParameterEntity getSysParameter(String code) {
        SysParameterEntity entity = new SysParameterEntity();
        entity.setId(1);
        entity.setCode("code a");
        entity.setValue("value a");
        entity.setCreateTime(new Date());
        return entity;
    }

    @Override
    public List<SysParameterEntity> getSysParameterList() {
        List<SysParameterEntity> sysParameterEntities = new ArrayList<>();
        SysParameterEntity entity = new SysParameterEntity();
        entity.setId(1);
        entity.setCode("code a");
        entity.setValue("value a");
        entity.setCreateTime(new Date());
        sysParameterEntities.add(entity);

        entity = new SysParameterEntity();
        entity.setId(2);
        entity.setCode("code b");
        entity.setValue("value b");
        entity.setCreateTime(new Date());
        sysParameterEntities.add(entity);
        return sysParameterEntities;
    }
}
