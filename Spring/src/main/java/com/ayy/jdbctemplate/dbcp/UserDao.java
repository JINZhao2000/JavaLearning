package com.ayy.jdbctemplate.dbcp;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @ ClassName UserDao
 * @ Description prepare for the C3P0
 * @ Author Zhao JIN
 * @ Date 30/10/2020 21:02
 * @ Version 1.0
 */

public class UserDao {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void update(User user){
        String sql = "update s_user set s_username = ?, p_password = ? where id = ?";
        Object[] args = {user.getUsername(),user.getPassword(),user.getId()};
        jdbcTemplate.update(sql,args);
    }
}
