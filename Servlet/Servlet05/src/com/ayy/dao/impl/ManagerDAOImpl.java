package com.ayy.dao.impl;

import com.ayy.dao.ManagerDAO;
import com.ayy.entities.Manager;
import com.ayy.utils.DBUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 10/02/2021
 * @ Version 1.0
 */
public class ManagerDAOImpl implements ManagerDAO {
    private QueryRunner runner = new QueryRunner();

    @Override
    public Manager select(String uname) {
        String sql = "select * from manager where uname=?";
        Manager manager = null;
        try {
            manager = runner.query(DBUtils.getConnection(), sql, new BeanHandler<>(Manager.class), uname);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return manager;
    }
}
