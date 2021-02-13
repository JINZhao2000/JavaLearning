package com.ayy.service.impl;

import com.ayy.dao.EmpDAO;
import com.ayy.dao.impl.EmpDAOImpl;
import com.ayy.entities.Emp;
import com.ayy.service.EmpService;
import com.ayy.utils.DBUtils;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 12/02/2021
 * @ Version 1.0
 */
public class EmpServiceImpl implements EmpService {
    private EmpDAO empDAO = new EmpDAOImpl();

    @Override
    public List<Emp> showAllEmp() {
        List<Emp> empList = null;
        try {
            DBUtils.beginTX();
            List<Emp> temps = empDAO.selectAll();
            if(temps!=null){
                empList = temps;
            }
            DBUtils.commit();
        }catch (Exception e){
            DBUtils.rollback();
        }
        return empList;
    }

    @Override
    public int removeEmp(int eid) {
        int result = 0;
        try {
            DBUtils.beginTX();
            result = empDAO.delete(eid);
            DBUtils.commit();
        }catch (Exception e){
            DBUtils.rollback();
        }
        return result;
    }

    @Override
    public int modifyEmp(Emp emp) {
        int result = 0;
        try {
            DBUtils.beginTX();
            result = empDAO.update(emp);
            DBUtils.commit();
        }catch (Exception e){
            DBUtils.rollback();
        }
        return result;
    }

    @Override
    public Emp showEmp(int eid) {
        Emp emp = null;
        try {
            DBUtils.beginTX();
            emp = empDAO.select(eid);
            DBUtils.commit();
        }catch (Exception e){
            DBUtils.rollback();
        }
        return emp;
    }
}
