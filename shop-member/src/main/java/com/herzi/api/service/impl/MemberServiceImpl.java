package com.herzi.api.service.impl;

import com.herzi.api.entity.UserEntity;
import com.herzi.api.service.MemberService;
import com.herzi.base.BaseApiService;
import com.herzi.base.ResponseBase;
import com.herzi.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/member")
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
}

