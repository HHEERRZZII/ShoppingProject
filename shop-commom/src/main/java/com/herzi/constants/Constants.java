package com.herzi.constants;

public interface Constants {
    // 响应code
    String HTTP_RES_CODE_NAME = "code";
    // 响应msg
    String HTTP_RES_CODE_MSG = "msg";
    // 响应data
    String HTTP_RES_CODE_DATA = "data";
    // 响应请求成功
    String HTTP_RES_CODE_200_VALUE = "success";
    // 系统错误
    String HTTP_RES_CODE_500_VALUE = "fial";
    // 响应请求成功code
    Integer HTTP_RES_CODE_200 = 200;
    //未关联QQ账号
    Integer HTTP_RES_CODE_201 = 201;
    // 系统错误
    Integer HTTP_RES_CODE_500 = 500;

    //发送邮件
    String MSG_EMAIL = "email";

    //Token标识码
    String TOKEN_MEMBER = "TOKEN_MEMBER";
    //会员token有效期
    Long TOKEN_MEMBER_TIME = Long.valueOf(60 * 60* 24 * 90);
    int COOKIE_TOKEN_MEMBER_TIME = 60 * 60* 24 * 90;

    // cookie 会员 totoken 名称
    String COOKIE_MEMBER_TOKEN ="cookie_member_token";
}
