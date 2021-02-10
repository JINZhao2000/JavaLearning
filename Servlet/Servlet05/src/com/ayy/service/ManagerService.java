package com.ayy.service;

import com.ayy.entities.Manager;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 10/02/2021
 * @ Version 1.0
 */

public interface ManagerService {
    Manager login(String uname, String pwd);
}
