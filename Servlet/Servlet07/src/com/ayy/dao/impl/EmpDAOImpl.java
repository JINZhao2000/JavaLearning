package com.ayy.dao.impl;

import com.ayy.dao.EmpDAO;
import com.ayy.entities.Emp;
import com.ayy.utils.DBUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
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

    @Override
    public int delete(int eid) {
        String sql = "delete from emp where eid = ?";
        int result = 0;
        try {
            result = runner.update(DBUtils.getConnection(),sql,eid);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public int update(Emp emp) {
        int result = 0;
        String sql = "update emp set ename = ?, salary = ?, age = ? where eid = ?";
        try {
            result = runner.update(DBUtils.getConnection(),sql,emp.getEname(),emp.getSalary(),emp.getAge(),emp.getEid());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public Emp select(int eid) {
        Emp emp = null;
        String sql = "select * from emp where eid = ?";
        try {
            emp = runner.query(DBUtils.getConnection(),sql,new BeanHandler<>(Emp.class),eid);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return emp;
    }
}
