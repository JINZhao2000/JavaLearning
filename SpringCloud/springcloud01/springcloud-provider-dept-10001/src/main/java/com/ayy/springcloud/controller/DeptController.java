package com.ayy.springcloud.controller;

import com.ayy.springcloud.bean.Dept;
import com.ayy.springcloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 09/04/2021
 * @ Version 1.0
 */
@RestController
public class DeptController {
    private DeptService deptService;

    @Autowired
    public void setDeptService(DeptService deptService) {
        this.deptService = deptService;
    }
    @PostMapping("/dept/add")
    public boolean addDept(Dept dept){
        return deptService.addDept(dept);
    }

    @GetMapping("/dept/get/{nDept}")
    public Dept get(@PathVariable("nDept") int nDept){
        return deptService.queryDeptById(nDept);
    }

    @GetMapping("/dept/list")
    public List<Dept> queryAll(){
        return deptService.queryAll();
    }
}
