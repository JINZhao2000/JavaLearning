package com.ayy.jdbctemplate.c3p0;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

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
        Object[] args = {user.getsUsername(),user.getsPassword(),user.getId()};
        jdbcTemplate.update(sql,args);
    }

    public List<User> findAll () {
        String sql = "select * from s_user";
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(User.class));
    }
}
