package com.herzi.controller;

import com.herzi.api.entity.UserEntity;
import com.herzi.base.ResponseBase;
import com.herzi.constants.Constants;
import com.herzi.feign.MemberServiceFeign;
import com.herzi.utils.CookieUtil;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.oauth.Oauth;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;

@Controller
public class LoginController {

    @Autowired
    private MemberServiceFeign memberServiceFeign;
    private static final String LOGIN = "login" ;
    private static final String INDEX = "redirect:/" ;
    private static final String QQRELATION = "qqrelation" ;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGet() {
        return LOGIN;
    }

    // 登录请求具体提交实现
    @RequestMapping(value = "/login", method = RequestMethod.POST)
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
        //下面封装好的
        setCookie(memberToken,response);
        return INDEX;
    }
    //同样写个代码封装cookie
    private void setCookie(String memberToken, HttpServletResponse response) {
        //将token信息存放在cookie里面
        CookieUtil.addCookie(response, Constants.COOKIE_MEMBER_TOKEN, memberToken , Constants.COOKIE_TOKEN_MEMBER_TIME);
    }
    //生成QQ授权登陆链接
    @RequestMapping("/localQQLogin")
    public String localQQLogin(HttpServletRequest request) throws QQConnectException {
       String authorizeURL = new Oauth().getAuthorizeURL(request);
       //这方法最后要重定向到腾讯那边去，就是那个生成授权的代码。
        return "redirect: " + authorizeURL;
    }
    //自动登陆流程
    @RequestMapping("/localQQLoginCallback")
    public String localQQLoginCallback(HttpServletRequest request,HttpServletResponse response,HttpSession httpSession) throws QQConnectException{
        //这里QQ官方接口已经封装得很好了！
        //1.获取授权码code

        //2.使用授权码code获取accesstoken
        AccessToken accessTokenOj = new Oauth().getAccessTokenByRequest(request);
        if (accessTokenOj == null) {
            request.setAttribute("error", "QQ授权失败");
            return "error";
        }
        String accessToken = accessTokenOj.getAccessToken();
        if (accessToken ==null) {
            request.setAttribute("error", "accessToken为null");
            return "error";
        }
        //3.使用accesstoken获取openID
        OpenID openidOj = new OpenID(accessToken);
        String userOpenId = openidOj.getUserOpenID();
        //4.调用会员服务接口，使用userOpenId 查找是否已经关联过账号
        ResponseBase openUserBase = memberServiceFeign.findByOpenIdUser(userOpenId);
        if (openUserBase.getRtnCode().equals(Constants.HTTP_RES_CODE_201)) {
            //5.如果没有关联过账号。跳转到关联账号页面
            httpSession.setAttribute("qqOpenid", userOpenId);
            return QQRELATION;
        }
        //6.已经绑定账号 自动登陆 将用户token信息存放在cookie中
        LinkedHashMap dataTokenMap = (LinkedHashMap) openUserBase.getObject();
        String memberToken = (String) dataTokenMap.get("memberToken");
        setCookie(memberToken,response);
        return INDEX;
    }
    //方法都是一环扣一环的！！！
    //这是QQ授权关联页面已有账号,流程跟上面的登陆类似
    @RequestMapping(value = "/qqRelation", method = RequestMethod.POST)
    public String qqRelation(UserEntity userEntity, HttpServletRequest request, HttpServletResponse response,HttpSession httpSession) {

        // 1.获取sessionid，是从上面的httpsession中来的
       String qqOpenid = (String) httpSession.getAttribute("qqOpenid");
       if (StringUtils.isEmpty(qqOpenid)) {
           request.setAttribute("error", "没有获取到openID");
           return "error";
       }
        // 2.调用登录接口，获取token信息
        userEntity.setOpenid(qqOpenid);
        ResponseBase loginBase = memberServiceFeign.qqLogin(userEntity);
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
        //下面封装好的
        setCookie(memberToken,response);
        return INDEX;
    }
}
