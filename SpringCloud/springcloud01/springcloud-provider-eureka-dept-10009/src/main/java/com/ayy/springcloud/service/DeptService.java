package com.ayy.springcloud.service;

import com.ayy.springcloud.bean.Dept;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 09/04/2021
 * @ Version 1.0
 */

public interface DeptService {
    boolean addDept(Dept dept);

    Dept queryDeptById(int nDept);

    List<Dept> queryAll();
}
