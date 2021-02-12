package com.ayy.dao;

import com.ayy.entities.EmpManager;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 12/02/2021
 * @ Version 1.0
 */

public interface EmpManagerDAO {
    EmpManager select(String uname);
}
