package com.ayy.service;

import com.ayy.bean.User;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 31/03/2021
 * @ Version 1.0
 */

public interface UserService {
    User queryUserByName(String name);
}
