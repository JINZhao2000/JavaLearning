package com.ayy.dao;

import com.ayy.entities.Admin;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 05/02/2021
 * @ Version 1.0
 */

public interface AdminDAO {
    int insert(Admin admin);
    int delete(String uname);
    int update(Admin admin);
    Admin select(String uname);
    List<Admin> selectAll();
}
