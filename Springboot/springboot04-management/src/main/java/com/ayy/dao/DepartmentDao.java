package com.ayy.dao;

import com.ayy.bean.Department;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 10/03/2021
 * @ Version 1.0
 */
@Repository
public class DepartmentDao {
    private static Map<Integer, Department> departments = null;

    static {
        departments = new HashMap<>();
        departments.put(1,new Department(1,"Info"));
        departments.put(2,new Department(2,"Chimie"));
        departments.put(3,new Department(3,"Physique"));
        departments.put(4,new Department(4,"Mecanique"));
    }

    public Collection<Department> getDepartments(){
        return departments.values();
    }

    public Department getDepartmentById(int id){
        return departments.get(id);
    }
}
