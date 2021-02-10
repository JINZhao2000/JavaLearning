package com.ayy.service;

import com.ayy.entities.Admin;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 06/02/2021
 * @ Version 1.0
 */

public interface AdminService {
    Admin login(String uname, String pwd);
    List<Admin> showAllAdmin();
}
