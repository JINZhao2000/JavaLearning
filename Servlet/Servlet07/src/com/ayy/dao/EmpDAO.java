package com.ayy.dao;

import com.ayy.entities.Emp;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 12/02/2021
 * @ Version 1.0
 */

public interface EmpDAO {
    List<Emp> selectAll();

    int delete(int eid);

    int update(Emp emp);

    Emp select(int eid);
}
