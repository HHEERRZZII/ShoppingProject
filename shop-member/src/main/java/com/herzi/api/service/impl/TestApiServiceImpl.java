package com.herzi.api.service.impl;

import com.herzi.api.service.TestApiService;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestApiServiceImpl implements TestApiService{

    @Override
    public Map<String, Object> test(Integer id, String name) {
        Map<String,Object> result = new HashMap<>();
        result.put("rtnCode","200");
        result.put("rtnMsg","success");
        result.put("data", "id :" + id + ", name :" + name);
        return result;
    }
}
