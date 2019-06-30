package com.herzi.api.service;

import com.alibaba.druid.sql.visitor.functions.If;
import com.herzi.api.entity.UserEntity;
import com.herzi.base.ResponseBase;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    ResponseBase findByTokenUser(@RequestParam("token")  String token);

    //使用openID查找用户信息
    @RequestMapping("/findByOpenIdUser")
    ResponseBase findByOpenIdUser(@RequestParam("openid")  String openid);
    //用户登陆
    @RequestMapping("qqLogin")
    ResponseBase qqLogin(@RequestBody UserEntity userEntity);


}
