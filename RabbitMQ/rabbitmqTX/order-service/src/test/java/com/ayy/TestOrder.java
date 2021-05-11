package com.ayy;

import com.ayy.bean.Order;
import com.ayy.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 11/05/2021
 * @ Version 1.0
 */
@SpringBootTest
public class TestOrder {
    @Autowired
    private OrderService orderService;

    @Test
    void testMakeOrder(){
        orderService.makeOrder(new Order("order","user","ocontent",new Date()));
    }
}
