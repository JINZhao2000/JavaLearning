package com.ayy.dao;

import com.ayy.bean.User;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 22/02/2021
 * @ Version 1.0
 */

public interface UserDao {
    /**
     * Register
     * @param user
     */
    void save(User user);
}
