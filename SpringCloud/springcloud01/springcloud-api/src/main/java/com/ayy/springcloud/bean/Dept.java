package com.ayy.springcloud.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 09/04/2021
 * @ Version 1.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Dept implements Serializable {
    private int nDept;
    private String nameDept;
    private String dbSource;

    public Dept(String nameDept) {
        this.nameDept = nameDept;
    }
}
