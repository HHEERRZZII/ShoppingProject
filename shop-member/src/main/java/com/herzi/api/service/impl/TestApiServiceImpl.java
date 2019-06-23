package com.herzi.api.service.impl;

import com.herzi.api.service.TestApiService;
import com.herzi.base.BaseApiService;
import com.herzi.base.ResponseBase;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestApiServiceImpl extends BaseApiService implements TestApiService{

    @Override
    public Map<String, Object> test(Integer id, String name) {
        Map<String,Object> result = new HashMap<>();
        result.put("rtnCode","200");
        result.put("rtnMsg","success");
        result.put("data", "id :" + id + ", name :" + name);
        return result;
    }

    @Override
    public ResponseBase testResponseBase() {
        //其实就是返回方法，方法里面有封装好的数据！
        return setResultSuccess();
    }

    @Override
    public ResponseBase settestRedis(String key, String value) {
         baseRedisService.setString(key, value, null);
         return setResultSuccess();
    }
}
