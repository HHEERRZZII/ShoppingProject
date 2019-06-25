package com.herzi.email.service;

import com.alibaba.fastjson.JSONObject;
import com.herzi.adapter.MessageAdapter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

//处理第三方发送邮件
@Service
@Slf4j
public class EmailService implements MessageAdapter {

    @Override
    public void sendMsg(JSONObject body) {
        //body里面就是队列的json类型的信息
        String email = body.getString("email");
        if (StringUtils.isEmpty(email)) {
            return;
        }
        log.info("消息服务平台发送邮件:{}",email);
    }
}
