package com.ayy.service;

import com.ayy.exception.UserException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 21/02/2021
 * @ Version 1.0
 */
public interface UserService {
    void delete(int id) throws UserException;
}
