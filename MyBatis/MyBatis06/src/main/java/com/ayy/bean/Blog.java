package com.ayy.bean;

import lombok.Data;

import java.util.Date;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 02/03/2021
 * @ Version 1.0
 */
@Data
public class Blog {
    private String bid;
    private String title;
    private String author;
    private Date createTime;
    private int views;
}
