package com.ayy.service.impl;

import com.ayy.dao.EmpManagerDAO;
import com.ayy.dao.impl.EmpManagerDAOImpl;
import com.ayy.entities.EmpManager;
import com.ayy.service.EmpManagerService;
import com.ayy.utils.DBUtils;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 12/02/2021
 * @ Version 1.0
 */
public class EmpManagerServiceImpl implements EmpManagerService {
    private EmpManagerDAO empManagerDAO = new EmpManagerDAOImpl();

    @Override
    public EmpManager login(String uname, String password) {
        EmpManager empManager = null;
        try {
            DBUtils.beginTX();
            EmpManager temp = empManagerDAO.select(uname);
            if(temp!=null && temp.getPwd().equals(password)){
                empManager = temp;
            }
            DBUtils.commit();
        }catch (Exception e){
            DBUtils.rollback();
        }
        return empManager;
    }
}
