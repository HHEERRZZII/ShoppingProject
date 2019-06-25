package com.herzi.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.herzi.api.entity.UserEntity;
import com.herzi.api.service.MemberService;
import com.herzi.base.BaseApiService;
import com.herzi.base.ResponseBase;
import com.herzi.constants.Constants;
import com.herzi.dao.MemberDao;
import com.herzi.mq.RegisterMailboxProducer;
import com.herzi.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MemberServiceImpl extends BaseApiService implements MemberService {

    @Autowired
    private RegisterMailboxProducer registerMailboxProducer;

    @Autowired
    private MemberDao memberDao;

    @Value("${messages.queue}")
    //绑定yml配置文件，然后赋值，读取配置文件的信息！！！要留意！
    private String MESSAGESQUEUE;

    @Override
    public ResponseBase findUserById(Long userId) {
        UserEntity user = memberDao.findByID(userId);
        if (user == null) {
            return setResultError("为查找到用户信息..");
        }
        return setResultSuccess(user);
    }

    @Override
    public ResponseBase registerUser(@RequestBody UserEntity user) {
        //密码加盐
        String password = user.getPassword();
        if (StringUtils.isEmpty(password)) {
            setResultError("输入的密码不能为空");
        }
        String newPassword = MD5Util.MD5(password);
        user.setPassword(newPassword);
        //Dao中返回的就是新增成功的个数...........别想复杂了！
        Integer code =  memberDao.insertUser(user);
        if (code <= 0) {
            return setResultError("新增用户失败......");
        }
        //异步的方式发送消息
        String email = user.getEmail();
        String json = emailJson(email);
        log.info("########会员消息已经推送到消息服务平台######## json:{}", json);
        sendMsg(json);
        return setResultSuccess("添加用户成功！！！");
    }
    //封装
    private String emailJson(String email) {
        JSONObject rootJson = new JSONObject();
        JSONObject header = new JSONObject();
        header.put("interfaceType", Constants.MSG_EMAIL);
        JSONObject content = new JSONObject();
        content.put("email", email);
        rootJson.put("header",header );
        rootJson.put("content", content);
        return rootJson.toString();
    }

    private void sendMsg(String json) {
        ActiveMQQueue activeMQQueue = new ActiveMQQueue(MESSAGESQUEUE);
        registerMailboxProducer.sendMsg(activeMQQueue,json);
    }
}

