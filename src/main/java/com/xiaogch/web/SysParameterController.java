package com.xiaogch.web;

import com.xiaogch.common.http.response.PagedResponse;
import com.xiaogch.common.http.response.Response;
import com.xiaogch.dao.SysParameterDao;
import com.xiaogch.entity.SysParameterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/31 0031.
 */
@RestController
@RequestMapping("sys/parameter")
public class SysParameterController extends BaseController {

    @Autowired
    SysParameterDao sysParameterDao;

    @RequestMapping("get/{code}")
    public Object getByCode(@PathVariable String code) {
        SysParameterEntity data = sysParameterDao.selectByCode(code);
        return Response.buildSuccessRsp(data);
    }

    @RequestMapping("add/{code}")
    public Object add(@PathVariable String code , @RequestParam(value = "value") String value) {
        SysParameterEntity sysParameterEntity = new SysParameterEntity();
        sysParameterEntity.setCode(code);
        sysParameterEntity.setValue(value);
        sysParameterDao.insert(sysParameterEntity);
        return Response.buildSuccessRsp();
    }

    @RequestMapping("update/{code}")
    public Object update(@PathVariable String code , @RequestParam(value = "value") String value) {
        int result = sysParameterDao.updateValueByCode(code , value);
        return result >= 1 ? "success" : "failure";
    }

    @RequestMapping("delete/{code}")
    public Object delete(@PathVariable String code) {
        int result = sysParameterDao.deleteByCode(code);
        Map<String  , Object> data = new HashMap<>();
        data.put("success" , true);
        data.put("success" , result >= 1 ? "delete record" : "record not exist");
        return Response.buildSuccessRsp(data);

    }

    @RequestMapping("getList")
    public Object getList(@RequestParam(value = "offset") Integer offset ,
                          @RequestParam(value = "size") Integer size) {
        List<SysParameterEntity> list = sysParameterDao.selectList(offset, size);
        PagedResponse data = new PagedResponse(offset , list);
        return Response.buildSuccessRsp(data);
    }

}
