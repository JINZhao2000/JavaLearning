package com.ayy;

import com.ayy.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 05/05/2021
 * @ Version 1.0
 */
@SpringBootTest
public class RabbitMQProducerApplicationTest {
    @Autowired
    private OrderService orderService;

    @Test
    public void testTTLQueue(){
        orderService.makeOrderTTL("user", "product", 10);
    }
}
