package com.ayy.dao;

import com.ayy.bean.User;

import java.util.List;
import java.util.Map;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 25/02/2021
 * @ Version 1.0
 */

public interface UserMapper {
    List<User> getAllUser();

    List<User> getUserLike(String value);

    User getUserById(int id);

    void addUserByMap(Map<String,Object> map);

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(int id);
}
