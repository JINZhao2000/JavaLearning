package com.ayy.service.impl;

import com.ayy.bean.Order;
import com.ayy.dao.OrderDao;
import com.ayy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 11/05/2021
 * @ Version 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void makeOrder(Order order) {
        orderDao.insert(order);

        String result = dispatcherHttpApi(order.getOrderId());

        if(!"success".equals(result)){
            throw new IllegalArgumentException("??? Order");
        }
    }

    private String dispatcherHttpApi(String orderId){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        factory.setConnectTimeout(3000);
        factory.setReadTimeout(2000);
        String url = "http://localhost:8001/order/"+orderId;
        RestTemplate restTemplate = new RestTemplate(factory);

        return restTemplate.getForObject(url, String.class);
    }
}
