package com.ayy.dao.impl;

import com.ayy.bean.Dispatcher;
import com.ayy.dao.DispatcherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 11/05/2021
 * @ Version 1.0
 */
@Repository
public class DispatcherDaoImpl implements DispatcherDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(Dispatcher dispatcher) {
        String sql = "insert into dispatchertable(dispatcher_id, order_id, dispatcher_status, order_content, create_time, user_id) value (?,?,?,?,?,?)";
        int count = jdbcTemplate.update(sql, dispatcher.getDispatcherId(), dispatcher.getOrderId(), dispatcher.getDispatcherStatus(), dispatcher.getOrderContent(), dispatcher.getCreateTime(), dispatcher.getUserId());
        if(count!=1){
            throw new IllegalArgumentException("??? Dispatcher");
        }
    }
}
