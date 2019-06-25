package com.herzi.api.service;

import com.herzi.api.entity.UserEntity;
import com.herzi.base.ResponseBase;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/member")
public interface MemberService {

    //使用userId查找用户信息
    @RequestMapping("/findUserById")
    ResponseBase findUserById(Long userId);

    //注册实现
    @RequestMapping("/registerUser")
    ResponseBase registerUser(@RequestBody UserEntity user);
}
