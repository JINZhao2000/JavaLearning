package com.ayy.service;

import com.ayy.entities.EmpManager;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 12/02/2021
 * @ Version 1.0
 */

public interface EmpManagerService {
    EmpManager login(String uname, String password);
}
