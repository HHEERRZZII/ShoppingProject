package com.herzi.controller;

import com.herzi.api.entity.UserEntity;
import com.herzi.base.ResponseBase;
import com.herzi.constants.Constants;
import com.herzi.feign.MemberServiceFeign;
import com.herzi.utils.CookieUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;

@Controller
public class LoginController {

    @Autowired
    private MemberServiceFeign memberServiceFeign;
    private static final String LOGIN = "login" ;
    private static final String INDEX = "redirect:/" ;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGet() {
        return LOGIN;
    }

    // 登录请求具体提交实现
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPost(UserEntity userEntity, HttpServletRequest request, HttpServletResponse response) {

        // 1.验证参数
        // 2.调用登录接口，获取token信息
        ResponseBase loginBase = memberServiceFeign.login(userEntity);
        if (!loginBase.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
            request.setAttribute("error", "账号或密码错误");
            return LOGIN;
        }

        LinkedHashMap loginData = (LinkedHashMap) loginBase.getObject();
        String memberToken = (String) loginData.get("memberToken");
        if (StringUtils.isEmpty(memberToken)) {
            request.setAttribute("error", "会话已经过期");
            return LOGIN;
        }
        //将token信息存放在cookie里面
        CookieUtil.addCookie(response, Constants.COOKIE_MEMBER_TOKEN, memberToken , Constants.COOKIE_TOKEN_MEMBER_TIME);
        return INDEX;
    }
}
