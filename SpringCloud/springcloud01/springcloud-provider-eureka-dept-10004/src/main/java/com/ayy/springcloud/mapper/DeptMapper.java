package com.ayy.springcloud.mapper;

import com.ayy.springcloud.bean.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 09/04/2021
 * @ Version 1.0
 */
@Mapper
@Repository
public interface DeptMapper {
    boolean addDept(Dept dept);

    Dept queryDeptById(@Param("nDept") int nDept);

    List<Dept> queryAll();
}
