package com.herzi.mq;

import com.alibaba.fastjson.JSONObject;
import com.herzi.adapter.MessageAdapter;
import com.herzi.constants.Constants;
import com.herzi.email.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

//消息监听接口
@Component
@Slf4j
public class ConsumerDistribute {

    @Autowired
    private EmailService emailService;

    private MessageAdapter messageAdapter;

    @JmsListener(destination = "messages_queue")
    public void distribute(String json){
        log.info("##########消息服务平台接受消息内容:{}#####",json);
        if (StringUtils.isEmpty(json)) {
            return;
        }
        //把
        JSONObject rootObject = new JSONObject().parseObject(json);
        JSONObject header = rootObject.getJSONObject("header");
        String interfaceType = header.getString("interfaceType");
        if (StringUtils.isEmpty(interfaceType)){
            return;
        }
        if (interfaceType.equals(Constants.MSG_EMAIL)) {
            //调用第三方邮件接口
            //这一步一定要理解，即不是单独的发送，而是统一起来，让一个接口发送！因为项目中还会有各种各样发送信息的方式！
            //这里采用的是赋值的方式，请一定注意，这里就是让发送接口统一发送！简洁和规范！
            messageAdapter = emailService;
        }
        //即如果信息匹配不上的话，就返回。因为传入的是json类型的，如果不是，就返回。
        //而一旦类型对上，连注入的类，定义好的变量，也会一并继承........牛逼！
        if (messageAdapter == null) {
            return;
        }
         JSONObject contentJson = rootObject.getJSONObject("content");
        messageAdapter.sendMsg(contentJson);
    }
}
