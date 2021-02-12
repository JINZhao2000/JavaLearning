package com.ayy.dao.impl;

import com.ayy.dao.EmpManagerDAO;
import com.ayy.entities.EmpManager;
import com.ayy.utils.DBUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 12/02/2021
 * @ Version 1.0
 */
public class EmpManagerDAOImpl implements EmpManagerDAO {
    private QueryRunner runner = new QueryRunner();

    @Override
    public EmpManager select(String uname) {
        String sql = "select * from empmanager where uname = ?";
        EmpManager empManager = null;
        try {
            empManager = runner.query(DBUtils.getConnection(),sql,new BeanHandler<>(EmpManager.class),uname);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return empManager;
    }
}
