package com.ayy.dao;

import com.ayy.bean.Department;
import com.ayy.bean.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 10/03/2021
 * @ Version 1.0
 */
@Repository
public class EmployeeDao {
    private static Map<Integer, Employee> employees;
    @Autowired
    private static DepartmentDao departmentDao;
    private static Integer primaryKey = 101;

    static {
        employees = new HashMap<>();
        employees.put(primaryKey,new Employee(primaryKey,"Emp1",0,new Department(1,"Info"),new Date()));primaryKey++;
        employees.put(primaryKey,new Employee(primaryKey,"Emp2",1,new Department(2,"Chimie"),new Date()));primaryKey++;
        employees.put(primaryKey,new Employee(primaryKey,"Emp3",2,new Department(3,"Physique"),new Date()));primaryKey++;
        employees.put(primaryKey,new Employee(primaryKey,"Emp4",3,new Department(4,"Mecanique"),new Date()));primaryKey++;
        employees.put(primaryKey,new Employee(primaryKey,"Emp5",4,new Department(1,"Info"),new Date()));primaryKey++;
    }

    public void addEmployee(Employee employee){
        if(employee.getId()==null){
            employee.setId(primaryKey);
            primaryKey++;
        }
        employees.put(employee.getId(),employee);
    }

    public Collection<Employee> getAllEmployees(){
        return employees.values();
    }

    public Employee getEmployeeById(int id){
        return employees.get(id);
    }

    public void deleteEmployee(int id){
        employees.remove(id);
    }
}
