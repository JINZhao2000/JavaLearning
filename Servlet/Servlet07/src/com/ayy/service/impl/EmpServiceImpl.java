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
}
