package com.ayy.dao.impl;

import com.ayy.dao.AdminDAO;
import com.ayy.entities.Admin;
import com.ayy.utils.DBUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 05/02/2021
 * @ Version 1.0
 */
public class AdminDAOImpl implements AdminDAO {
    private QueryRunner runner = new QueryRunner();
    
    @Override
    public int insert(Admin admin) {
        return 0;
    }

    @Override
    public int delete(String uname) {
        return 0;
    }

    @Override
    public int update(Admin admin) {
        return 0;
    }

    @Override
    public Admin select(String uname) {
        String sql = "select * from admin where uname = ?";
        Admin admin = null;
        try {
            admin = runner.query(DBUtils.getConnection(),sql, new BeanHandler<>(Admin.class),uname);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return admin;
    }

    @Override
    public List<Admin> selectAll() {
        String sql = "select * from admin";
        List<Admin> admins = null;
        try {
            admins = runner.query(DBUtils.getConnection(),sql,new BeanListHandler<Admin>(Admin.class));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return admins;
    }
}
