package com.ayy.jdbctemplate.properties;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @ ClassName UserDao
 * @ Description prepare for the properties
 * @ Author Zhao JIN
 * @ Date 30/10/2020 21:02
 * @ Version 1.0
 */

public class UserDao extends JdbcDaoSupport {

    public void update(User user){
        String sql = "update s_user set s_username = ?, p_password = ? where id = ?";
        Object[] args = {user.getsUsername(),user.getsPassword(),user.getId()};
        this.getJdbcTemplate().update(sql,args);
    }

    public List<User> findAll () {
        String sql = "select * from s_user";
        return this.getJdbcTemplate().query(sql,BeanPropertyRowMapper.newInstance(User.class));
    }

    public User getById (int id) {
        String sql = "select * from s_user where id = ?";
        return this.getJdbcTemplate().queryForObject(sql,BeanPropertyRowMapper.newInstance(User.class),id);
    }
}
