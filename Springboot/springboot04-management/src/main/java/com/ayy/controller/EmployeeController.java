package com.ayy.controller;

import com.ayy.bean.Department;
import com.ayy.bean.Employee;
import com.ayy.dao.DepartmentDao;
import com.ayy.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 13/03/2021
 * @ Version 1.0
 */
@Controller
public class EmployeeController {
    private EmployeeDao employeeDao;
    private DepartmentDao departmentDao;

    @RequestMapping("/emps")
    public String list(Model model){
        Collection<Employee> allEmployees = employeeDao.getAllEmployees();
        model.addAttribute("emps",allEmployees);
        return "/emp/list";
    }

    @PostMapping("/empAdd")
    public String add(Employee employee){
        employeeDao.addEmployee(employee);
        return "redirect:/emps";
    }

    @GetMapping("/empAdd")
    public String toAdd(Model model){
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("departs",departments);
        return "/emp/add";
    }

    @GetMapping("/emp/{id}")
    public String toUpdate(@PathVariable("id") Integer id, Model model){
        Employee employee = employeeDao.getEmployeeById(id);
        Collection<Department> department = departmentDao.getDepartments();
        model.addAttribute("departs",department);
        model.addAttribute("emp",employee);
        return "/emp/update";
    }

    @PostMapping("/updateEmp")
    public String update(Employee employee){
        employee.setDepartment(departmentDao.getDepartmentById(employee.getDepartment().getId()));
        employeeDao.addEmployee(employee);
        return "redirect:/emps";
    }

    @GetMapping("/delemp/{id}")
    public String delete(@PathVariable("id") int id){
        employeeDao.deleteEmployee(id);
        return "redirect:/emps";
    }
    @Autowired
    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Autowired
    public void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }
}
