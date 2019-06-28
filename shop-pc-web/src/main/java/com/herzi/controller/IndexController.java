package com.herzi.controller;

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
import java.util.LinkedHashMap;

@Controller
public class IndexController {

    @Autowired
    private MemberServiceFeign memberServiceFeign;

    private static final String INDEX = "index";

    //主页
    @RequestMapping(value = "/" , method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        //1.从cookie中获取token信息
       String token = CookieUtil.getUid(request, Constants.COOKIE_MEMBER_TOKEN);
       //2.如果cookie存在 token，调用会员服务接口，使用token查询用户信息
        if (!StringUtils.isEmpty(token)) {
            ResponseBase responseBase = memberServiceFeign.findByTokenUser(token);
            if (responseBase.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
                LinkedHashMap userData = (LinkedHashMap) responseBase.getObject();
                String username = (String) userData.get("username");
                //setAttribute是给前端调用用的，要区分好！
                request.setAttribute("username", username);
            }
        }

        return INDEX;
    }
}
