package com.herzi.api.service;

import com.herzi.api.entity.PaymentInfo;
import com.herzi.base.ResponseBase;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/pay")
public interface PayService  {

    //创建支付令牌
    @RequestMapping("/createPayToken")
    public ResponseBase createPayToken(@RequestBody PaymentInfo paymentInfo);

    //使用支付令牌查找支付信息
    @RequestMapping("/findPayToken")
    public ResponseBase findPayToken(@RequestParam("payToken") String payToken);
}
