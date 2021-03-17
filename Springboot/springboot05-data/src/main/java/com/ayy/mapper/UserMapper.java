package com.ayy.mapper;

import com.ayy.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 17/03/2021
 * @ Version 1.0
 */
@Mapper
@Repository
public interface UserMapper {
    List<User> queryUserList();

    User queryUserById(@Param("uid") int id);

    int addUser(User user);

    int updateUser(User user);

    int deleteUser(@Param("uid") int id);
}
