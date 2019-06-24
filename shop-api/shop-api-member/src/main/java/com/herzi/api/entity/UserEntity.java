package com.herzi.api.entity;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserEntity {
    //实体类也要被别的类调用，所以要放在api接口中，记住，接口是被其他服务调用的，而不是私有的！
    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private Date created;
    private Date updated;
}
