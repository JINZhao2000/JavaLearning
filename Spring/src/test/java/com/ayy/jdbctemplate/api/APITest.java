package com.ayy.jdbctemplate.api;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @ ClassName APITest
 * @ Description test for the spring jdbc
 *                  setting apis
 * @ Author Zhao JIN
 * @ Date 30/10/2020 20:44
 * @ Version 1.0
 */
public class APITest {
    @Test
    public void test01(){
        // create DataSource dbcp
        BasicDataSource dataSource = new BasicDataSource();
        // 4 elements of DataSource
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.137.254:3306/springJDBCTemplate");
        dataSource.setUsername("jinzhao");
        dataSource.setPassword("18273645");
        // create Template
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        // use api
        jdbcTemplate.update("INSERT INTO s_user(s_username,s_password) VALUES(?,?);","c","1234");
    }
}
