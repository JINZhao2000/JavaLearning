package com.ayy.service.impl;

import com.ayy.dao.AdminDAO;
import com.ayy.dao.impl.AdminDAOImpl;
import com.ayy.entities.Admin;
import com.ayy.service.AdminService;
import com.ayy.utils.DBUtils;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 06/02/2021
 * @ Version 1.0
 */
public class AdminServiceImpl implements AdminService {
    private AdminDAO adminDAO = new AdminDAOImpl();

    @Override
    public Admin login(String uname, String pwd) {
        Admin result = null;
        Admin admin = adminDAO.select(uname);
        if (admin != null) {
            if (admin.getpwd().equals(pwd)) {
                result = admin;
            }
        }
        DBUtils.commit();
        return result;
    }

    @Override
    public List<Admin> showAllAdmin() {
        List<Admin> admins = null;
        admins = adminDAO.selectAll();
        DBUtils.commit();
        return admins;
    }
}
