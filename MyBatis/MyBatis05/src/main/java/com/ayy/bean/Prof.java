package com.ayy.bean;

import lombok.Data;

import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 01/03/2021
 * @ Version 1.0
 */
@Data
public class Prof {
    private int pid;
    private String pname;
    private List<Etu> etus;
}
