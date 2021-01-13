package com.ayy.MySQL.exo;

import java.sql.Connection;

/**
 * @ ClassName Ex1
 * @ Description
 * @ Author Zhao JIN
 * @ Date 12/01/2021 22H
 * @ Version 1.0
 */
public class Ex1 {
    public static void main(String[] args) {
        Connection connection = JDBCUtilsForAliyun.getConnection();
        String table_cust = "create table customers(cust_id int primary key auto_increment, cust_name varchar(20), email varchar(30), birth date)";
        String insert_cust = "insert into customers(cust_name,email,birth) values(?,?,?)";
        int result;
        result = JDBCUtilsForAliyun.update(table_cust);
        System.out.println(result==0?"Table is created":"Table can't be created");
        result = JDBCUtilsForAliyun.update(insert_cust,"A","a@b.c","2000-1-1");
        System.out.println(result==1?"A row inserted":"Insert failed");
        JDBCUtilsForAliyun.close(connection);
    }
}
