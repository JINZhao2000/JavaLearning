package com.ayy.dao;

import com.ayy.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 25/02/2021
 * @ Version 1.0
 */

public interface UserMapper {
    @Select("select * from mb_user")
    List<User> getAllUser();

    @Select("select * from mb_user where uid = #{id}")
    User getUserById(@Param("id") int id);

    @Insert("insert into mb_user(uid,uname,pwd) values(${id},${name},#{password})")
    void addUser(User user);

    @Update("update mb_user set uname=#{name}, pwd=#{password} where uid=#{id}")
    void updateUser(User user);

    @Delete("delete from mb_user where uid=#{id}")
    void deleteUser(@Param("id") int id);
}
