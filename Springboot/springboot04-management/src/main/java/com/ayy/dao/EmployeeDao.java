package com.ayy.dao;

import com.ayy.bean.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 10/03/2021
 * @ Version 1.0
 */
@Repository
@Lazy
public class EmployeeDao {
    private static Map<Integer, Employee> employees;
    @Autowired
    private static DepartmentDao departmentDao;
    private static Integer primaryKey = 101;

    static {
        employees = new HashMap<>();
        employees.put(primaryKey,new Employee(primaryKey,"Emp1",0,departmentDao.getDepartmentById(1),new Date()));primaryKey++;
        employees.put(primaryKey,new Employee(primaryKey,"Emp2",1,departmentDao.getDepartmentById(2),new Date()));primaryKey++;
        employees.put(primaryKey,new Employee(primaryKey,"Emp3",2,departmentDao.getDepartmentById(3),new Date()));primaryKey++;
        employees.put(primaryKey,new Employee(primaryKey,"Emp4",3,departmentDao.getDepartmentById(4),new Date()));primaryKey++;
        employees.put(primaryKey,new Employee(primaryKey,"Emp5",4,departmentDao.getDepartmentById(1),new Date()));primaryKey++;
    }

    public void addEmployee(Employee employee){
        if(employee.getId()==null){
            employee.setId(primaryKey);
            primaryKey++;
        }
        employees.put(employee.getId(),employee);
    }

    public List<Employee> getAllEmployees(){
        return (List<Employee>) employees.values();
    }

    public Employee getEmployeeById(int id){
        return employees.get(id);
    }

    public void deleteEmployee(int id){
        employees.remove(id);
    }
}
