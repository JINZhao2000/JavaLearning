package com.ayy.service;

import com.ayy.bean.Order;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 11/05/2021
 * @ Version 1.0
 */

public interface OrderService {
    void makeOrder(Order order, int status);
}
