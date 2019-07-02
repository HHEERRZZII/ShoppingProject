package com.herzi.controller;

import com.herzi.base.ResponseBase;
import com.herzi.constants.Constants;
import com.herzi.feign.PayServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

@Slf4j
@Controller
public class PayController {
    @Autowired
    private PayServiceFeign payServiceFeign;
    //使用token进行支付
    @RequestMapping("/aliPay")
    public void aliPay (String payToken, HttpServletResponse response) throws IOException{
        response.setContentType("text/html,charset=utf-8");
        PrintWriter writer = response.getWriter();
        //1.参数验证
        if (StringUtils.isEmpty(payToken)) {
            return;
        }
        //2.调用支付服务接口，获取支付宝HTML元素,通过payToken获取的
        ResponseBase payTokenResult = payServiceFeign.findPayToken(payToken);
        if (!payTokenResult.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
            String msg = payTokenResult.getMsg();
            writer.println(msg);
            return;
        }
        //3.返回可以执行的HTML元素给客户端
        LinkedHashMap data = (LinkedHashMap) payTokenResult.getObject();
        String payHtml = (String) data.get("payHtml");
        log.info("######PayController######payHtml:{}", payHtml);
        //4.页面上进行渲染
        writer.println(payHtml);
        writer.close();
    }
}
