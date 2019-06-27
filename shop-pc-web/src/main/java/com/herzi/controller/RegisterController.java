package com.herzi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegisterController {
    private static final String REGISTER = "register";
    //跳转到页面
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return REGISTER;
    }
}
