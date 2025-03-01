package com.ayy.dao.impl;

import com.ayy.bean.Order;
import com.ayy.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 11/05/2021
 * @ Version 1.0
 */
@Repository("orderDao")
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(Order order, int status) {
        String sql = "insert into ordertable(order_id, user_id, order_content, create_time) value(?,?,?,?)";
        jdbcTemplate.update(sql, order.getOrderId(), order.getUserId(),order.getOrderContent(), order.getCreateTime());
    }
}
