package com.herzi.api.service;

import com.alibaba.druid.sql.visitor.functions.If;
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

    //登陆功能实现
    //也是直接传入UserEntity信息
    @RequestMapping("/login")
    ResponseBase login(@RequestBody UserEntity user);

    //使用token登陆
    @RequestMapping("/findByTokenUser")
    ResponseBase findByTokenUser(String token);

}
