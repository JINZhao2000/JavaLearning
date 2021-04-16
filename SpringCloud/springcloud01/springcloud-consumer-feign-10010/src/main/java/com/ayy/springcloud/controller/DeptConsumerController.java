package com.ayy.springcloud.controller;

import com.ayy.springcloud.bean.Dept;
import com.ayy.springcloud.service.DeptClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 10/04/2021
 * @ Version 1.0
 */
@RestController
public class DeptConsumerController {
    private DeptClientService deptClientService;

    @Autowired
    public void setDeptClientService(DeptClientService deptClientService) {
        this.deptClientService = deptClientService;
    }

    @RequestMapping("/consumer/dept/get/{nDept}")
    public Dept get(@PathVariable("nDept") int nDept){
        return deptClientService.queryById(nDept);
    }

    @RequestMapping("/consumer/dept/add")
    public boolean add(Dept dept){
        return deptClientService.addDept(dept);
    }

    @RequestMapping("/consumer/dept/list")
    public List<Dept> list(){
        return deptClientService.queryAll();
    }
}
