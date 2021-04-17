package com.ayy.springcloud.service.impl;

import com.ayy.springcloud.bean.Dept;
import com.ayy.springcloud.mapper.DeptMapper;
import com.ayy.springcloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 09/04/2021
 * @ Version 1.0
 */
@Service
public class DeptServiceImpl implements DeptService {
    private DeptMapper deptMapper;

    @Autowired
    public void setDeptMapper(DeptMapper deptMapper) {
        this.deptMapper = deptMapper;
    }

    @Override
    public boolean addDept(Dept dept) {
        return deptMapper.addDept(dept);
    }

    @Override
    public Dept queryDeptById(int nDept) {
        return deptMapper.queryDeptById(nDept);
    }

    @Override
    public List<Dept> queryAll() {
        return deptMapper.queryAll();
    }
}
