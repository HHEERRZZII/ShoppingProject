package com.herzi.dao;

import com.herzi.api.entity.UserEntity;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MemberDao {

    @Select("select  id,username,password,phone,email,created,updated,openid from mb_user where id =#{userId}")
    UserEntity findByID(@Param("userId") Long userId);

    @Insert("INSERT  INTO `mb_user`  (username,password,phone,email,created,updated) VALUES (#{username}, #{password},#{phone},#{email},#{created},#{updated});")
    Integer insertUser(UserEntity userEntity);

    @Select("select  id,username,password,phone,email,created,updated,openid from mb_user where username =#{username} and password =#{password}")
    //账号密码一起验证，不要想复杂了，不是分开验证的！
    UserEntity login(@Param("username") String username ,@Param("password") String password);

    @Select("select  id,username,password,phone,email,created,updated,openid from mb_user where openid =#{openid}")
    UserEntity findByOpenIdUser(@Param("openid") String openid);
    //openid获取，要先登陆成功
    @Update("update mb_user set openid = #{openid} where id = #{userId}")
    Integer updateByOpenIdUser(@Param("openid") String openid,@Param("userId") Integer userId );

}
