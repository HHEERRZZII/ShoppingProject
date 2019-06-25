package com.herzi.email.service;

import com.alibaba.fastjson.JSONObject;
import com.herzi.adapter.MessageAdapter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

//处理第三方发送邮件
@Service
@Slf4j
public class EmailService implements MessageAdapter {

    @Value("${msg.subject}")
    private String subject;

    @Value("${msg.text}")
    private String text;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMsg(JSONObject body) {
        //body里面就是队列的json类型的信息
        String email = body.getString("email");
        //注意这种写法，email里面是一堆json数据，所以下面的全部可以复用!即直接传入email,里面有各自对应的信息。
        if (StringUtils.isEmpty(email)) {
            return;
        }
        log.info("消息服务平台发送邮件:{}",email);
       SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
       //从哪里来
        simpleMailMessage.setFrom(email);
        //到哪里取
        simpleMailMessage.setTo(email);
        //标题
        simpleMailMessage.setSubject(subject);
        //内容
        simpleMailMessage.setText(text.replace("{}",email));
        //直接调用接口发送,全部封装好了............
        javaMailSender.send(simpleMailMessage);
        log.info("消费者发送邮件完成");
    }
}
