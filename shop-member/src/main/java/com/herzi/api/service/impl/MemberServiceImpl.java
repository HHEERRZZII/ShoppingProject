package com.herzi.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.herzi.api.entity.UserEntity;
import com.herzi.api.service.MemberService;
import com.herzi.base.BaseApiService;
import com.herzi.base.BaseRedisService;
import com.herzi.base.ResponseBase;
import com.herzi.constants.Constants;
import com.herzi.dao.MemberDao;
import com.herzi.mq.RegisterMailboxProducer;
import com.herzi.utils.MD5Util;
import com.herzi.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //Dao中返回的就是新增成功的个数...........别想复杂了！
        Integer code =  memberDao.insertUser(user);
        if (code <= 0) {
            return setResultError("新增用户失败......");
        }
        //异步的方式发送消息
        String email = user.getEmail();
        //得到email的基本信息
        String json = emailJson(email);
        log.info("########会员消息已经推送到消息服务平台######## json:{}", json);
        sendMsg(json);
        return setResultSuccess("添加用户成功！！！");
    }

    @Override
    public ResponseBase login(@RequestBody UserEntity user) {
        //1.验证参数
        String username = user.getUsername();
        if (StringUtils.isEmpty(username)) {
            return setResultError("用户名称不能为空");
        }
        String password = user.getPassword();
        if (StringUtils.isEmpty(password)) {
            return setResultError("密码不能为空");
        }

        //2.数据库查找账号密码是否正确
        String newPassword = MD5Util.MD5(password);
        UserEntity userEntity = memberDao.login(username ,newPassword);
        if (userEntity == null) {
            return setResultError("用户名或密码错误");
        }
        //3.如果账号密码正确，对应生成token
        String memberToken = TokenUtils.getMemberToken();
        //4.存放在redis中，key为token，value 为 userID
        Integer userId = userEntity.getId();
        log.info("########用户信息token存放在redis中.... key 为：{}, value {}", memberToken , userId);
        baseRedisService.setString(memberToken, userId + "", Constants.TOKEN_MEMBER_TIME);
        //5.直接返回token
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("memberToken", memberToken);
        return setResultSuccess(jsonObject);
    }

    @Override
    public ResponseBase findByTokenUser(@RequestParam("token") String token) {
        //1.验证参数
        if (StringUtils.isEmpty(token)) {
            return setResultError("token不能为空!");
        }
        //2.从redis中使用token 查找对应 userID
        String strUserId = baseRedisService.getString(token);
        if (StringUtils.isEmpty(strUserId)) {
            return setResultError("token无效或者已经过期");
        }
        //3.使用userID数据库查询用户信息返回给客户端
        Long userId = Long.parseLong(strUserId);
        UserEntity userEntity = memberDao.findByID(userId);
        if (userEntity == null) {
            return setResultError("未查询到该用户信息");
        }
        //这一步是为了不展示密码
        userEntity.setPassword(null);
        return setResultSuccess(userEntity);
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

