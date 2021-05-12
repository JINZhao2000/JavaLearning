package com.ayy.service.impl;

import com.ayy.bean.Order;
import com.ayy.dao.OrderDao;
import com.ayy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 11/05/2021
 * @ Version 1.0
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    @Qualifier("orderDao")
    private OrderDao orderDao;

    @Override
    // @Transactional(rollbackFor = Exception.class)
    public void makeOrder(Order order, int status) {
        orderDao.insert(order, 0);

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
