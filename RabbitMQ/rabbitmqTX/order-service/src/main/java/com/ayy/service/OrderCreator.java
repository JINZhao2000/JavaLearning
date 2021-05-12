package com.ayy.service;

import com.ayy.bean.Order;
import com.ayy.service.impl.MQOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 12/05/2021
 * @ Version 1.0
 */
@Component
public class OrderCreator {
    @Autowired
    private OrderService orderService;

    @Autowired
    private MQOrderServiceImpl mqOrderService;

    public void createOrder(Order order) throws Exception{
        orderService.makeOrder(order, 0);
        mqOrderService.sendMessage(order);
    }
}
