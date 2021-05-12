package com.ayy.service.impl;

import com.ayy.bean.Order;
import com.ayy.dao.OrderDao;
import com.ayy.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 12/05/2021
 * @ Version 1.0
 */
@Service(value = "mqOrderService")
public class MQOrderServiceImpl implements OrderService {
    @Autowired
    @Qualifier("mqOrderDao")
    private OrderDao orderDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void makeOrder(Order order, int status) {
        orderDao.insert(order, status);
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void regCallBack(){
        rabbitTemplate.setConfirmCallback((data, ack, cause)->{
            System.out.println("cause : "+cause);
            String orderId = data.getId();
            if(!ack){
                System.out.println("failed");
                return;
            }
            try {
                String updatesql = "update ordertablemessage set status = 1 where order_id = ?";
                int count = jdbcTemplate.update(updatesql, orderId);
                if(count == 1){
                    System.out.println("success");
                }
            } catch (Exception e){
                System.out.println("failed");
            }
        });
    }

    public void sendMessage(Order order) throws JsonProcessingException {
        rabbitTemplate.convertAndSend("order_direct_exchange", "order", new ObjectMapper().writeValueAsString(order), new CorrelationData(order.getOrderId()));
    }
}
