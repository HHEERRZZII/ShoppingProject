package com.herzi.api.service;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/member")
public interface TestApiService {
    @RequestMapping("/test")
    public Map<String,Object> test(Integer id, String name);

}
