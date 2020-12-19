package com.ayy.MySQL.preparedStatement;

import java.sql.Date;

/**
 * @ClassName Order
 * @Description table Order
 * @Author Zhao JIN
 * @Date 29/10/2020 22:54
 * @Version 1.0
 */
public class Order {
    private int orderId;
    private Date orderDate;
    private int customerId;

    public Order () {}

    public Order (int orderId, Date orderDate, int customerId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
    }

    public int getOrderId () {
        return orderId;
    }

    public void setOrderId (int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate () {
        return orderDate;
    }

    public void setOrderDate (Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getCustomerId () {
        return customerId;
    }

    public void setCustomerId (int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString () {
        return "Order{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", customerId=" + customerId +
                '}';
    }
}
