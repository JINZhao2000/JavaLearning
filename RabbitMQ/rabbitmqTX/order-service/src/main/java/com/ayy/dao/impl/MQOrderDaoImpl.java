package com.ayy.dao.impl;

import com.ayy.bean.Order;
import com.ayy.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 12/05/2021
 * @ Version 1.0
 */
@Repository(value = "mqOrderDao")
public class MQOrderDaoImpl implements OrderDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(Order order, int status) {
        String sql = "insert into ordertablemessage(order_id, user_id, order_content, status) value(?,?,?,?)";
        jdbcTemplate.update(sql, order.getOrderId(), order.getUserId(),order.getOrderContent(), status);
    }
}
