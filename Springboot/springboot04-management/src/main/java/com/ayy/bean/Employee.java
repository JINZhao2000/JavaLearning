package com.ayy.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 10/03/2021
 * @ Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Integer id;
    private String employeeName;
    private Integer gender;
    private Department department;
    private Date birth;
}
