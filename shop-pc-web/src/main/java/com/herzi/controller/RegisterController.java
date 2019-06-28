package com.herzi.controller;

import com.herzi.api.entity.UserEntity;
import com.herzi.base.ResponseBase;
import com.herzi.constants.Constants;
import com.herzi.feign.MemberServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RegisterController {

    @Autowired
    private MemberServiceFeign memberServiceFeign;

    private static final String REGISTER = "register";

    private static final String LOGIN = "login";

    //跳转到页面
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerGet() {

        return REGISTER;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerPost(UserEntity userEntity, HttpServletRequest request) {
        //验证参数

        // 调用会员注册接口
        ResponseBase registerUser = memberServiceFeign.registerUser(userEntity);//一环扣一环的
        //失败，跳转到失败页面
        if (!registerUser.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
            request.setAttribute("error", "注册失败");
            return REGISTER;
        }
        //成功，跳转到成功页面
       return LOGIN;
    }

    //
}
