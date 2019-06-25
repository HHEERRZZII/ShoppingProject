package com.herzi.api.service.impl;

import com.herzi.api.entity.UserEntity;
import com.herzi.api.service.MemberService;
import com.herzi.base.BaseApiService;
import com.herzi.base.ResponseBase;
import com.herzi.dao.MemberDao;
import com.herzi.utils.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MemberServiceImpl extends BaseApiService implements MemberService {

    @Autowired
    private MemberDao memberDao;

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
        return setResultSuccess("添加用户成功！！！");
    }
}

