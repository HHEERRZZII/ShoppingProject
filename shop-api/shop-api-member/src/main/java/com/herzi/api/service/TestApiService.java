package com.herzi.api.service;

import com.herzi.base.ResponseBase;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/member")
public interface TestApiService {
    @RequestMapping("/test")
    public Map<String,Object> test(Integer id, String name);

    @RequestMapping("/testResponseBase")
    public ResponseBase testResponseBase();

    @RequestMapping("/testRedis")
    public ResponseBase settestRedis(String key, String value);

}
