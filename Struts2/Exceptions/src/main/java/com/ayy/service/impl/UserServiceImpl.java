package com.ayy.service.impl;

import com.ayy.exception.UserException;
import com.ayy.service.UserService;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 21/02/2021
 * @ Version 1.0
 */
public class UserServiceImpl implements UserService {
    @Override
    public void delete(int id) throws UserException {
        if(id==0){
            throw new UserException("Invalid index");
        }
        // dao
    }
}
