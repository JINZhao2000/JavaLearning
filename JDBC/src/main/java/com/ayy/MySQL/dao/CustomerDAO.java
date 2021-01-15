package com.ayy.MySQL.dao;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @ ClassName CustomerDAO
 * @ Description
 * @ Author Zhao JIN
 * @ Date 15/01/2021 22H
 * @ Version 1.0
 */
public interface CustomerDAO {
    void insert(Connection connection, Customer customer);

    void deleteById(Connection connection, int custId);

    void update(Connection connection, Customer customer);

    Customer getCustomerById(Connection connection, int custId);

    Date getBirthById(Connection connection, int custId);

    long getCount(Connection connection);

    List<Customer> getAll(Connection connection);
}
