package com.ayy;

import com.ayy.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitmqTopicProducerApplicationTests {
    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Test
    void contextLoads() {
        orderService.makeOrder("1", "1", 12);
    }
}
