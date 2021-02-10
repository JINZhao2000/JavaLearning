package com.ayy.service.impl;

import com.ayy.dao.ManagerDAO;
import com.ayy.dao.impl.ManagerDAOImpl;
import com.ayy.entities.Manager;
import com.ayy.service.ManagerService;
import com.ayy.utils.DBUtils;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 10/02/2021
 * @ Version 1.0
 */
public class ManagerServiceImpl implements ManagerService {
    private ManagerDAO managerDAO = new ManagerDAOImpl();

    @Override
    public Manager login(String uname, String pwd) {
        Manager manager = null;
        try {
            DBUtils.begin();
            Manager temp = managerDAO.select(uname);
            if (temp != null) {
                if (temp.getPwd().equals(pwd)) {
                    manager = temp;
                }
            }
            DBUtils.commit();
        }catch (Exception e){
            DBUtils.rollback();
        }
        return manager;
    }
}
