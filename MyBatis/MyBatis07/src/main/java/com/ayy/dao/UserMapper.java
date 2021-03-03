package com.ayy.dao;

import com.ayy.bean.User;
import org.apache.ibatis.annotations.Param;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 03/03/2021
 * @ Version 1.0
 */

public interface UserMapper {
    // List<User> queryAllUsers();

    User queryUserById(@Param("id") int id);
}
