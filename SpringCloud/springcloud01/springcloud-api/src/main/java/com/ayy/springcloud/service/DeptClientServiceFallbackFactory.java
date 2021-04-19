package com.ayy.springcloud.service;

import com.ayy.springcloud.bean.Dept;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 19/04/2021
 * @ Version 1.0
 */
@Component
public class DeptClientServiceFallbackFactory implements FallbackFactory<DeptClientService> {
    @Override
    public DeptClientService create(Throwable cause) {
        return new DeptClientService() {
            @Override
            public Dept queryById(int nDept) {
                return new Dept().setNDept(nDept).setNameDept("No data").setDbSource("No Data");
            }

            @Override
            public List<Dept> queryAll() {
                return null;
            }

            @Override
            public boolean addDept(Dept dept) {
                return false;
            }
        };
    }
}
