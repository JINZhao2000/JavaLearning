package com.ayy.dao.impl;

import com.ayy.dao.EmpDAO;
import com.ayy.entities.Emp;
import com.ayy.utils.DBUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 12/02/2021
 * @ Version 1.0
 */
public class EmpDAOImpl implements EmpDAO {
    private QueryRunner runner = new QueryRunner();

    @Override
    public List<Emp> selectAll() {
        String sql = "select * from emp";
        List<Emp> empList = null;
        try {
            empList = runner.query(DBUtils.getConnection(),sql,new BeanListHandler<>(Emp.class));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return empList;
    }
}
