package com.ayy.MySQL.dao2;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @ ClassName CustomerDAOImpl
 * @ Description
 * @ Author Zhao JIN
 * @ Date 15/01/2021 22H
 * @ Version 1.0
 */
public class CustomerDAOImpl extends BaseDAO<Customer> implements CustomerDAO {
    @Override
    public void insert(Connection connection, Customer customer) {
        String sql = "insert into customers(cust_id,cust_name,email,birth)values(?,?,?,?)";
        super.update(connection,sql,customer.getCust_id(),customer.getCust_name(),
                customer.getEmail(),customer.getBirth());
    }

    @Override
    public void deleteById(Connection connection, int custId) {
        String sql = "delete from customers where cust_id = ?";
        super.update(connection,sql,custId);
    }

    @Override
    public void update(Connection connection, Customer customer) {
        String sql = "update customers set cust_name = ? , email = ? , birth = ? where cust_id = ?";
        super.update(connection,sql,customer.getCust_name(),
                customer.getEmail(),customer.getBirth(),customer.getCust_id());
    }

    @Override
    public Customer getCustomerById(Connection connection, int custId) {
        String sql = "select cust_id, cust_name, email, birth from customers where cust_id = ?";
        return super.getInstance(connection,sql,custId);
    }

    @Override
    public Date getBirthById(Connection connection, int custId) {
        String sql = "select birth from customers where cust_id = ?";
        return getValue(Date.class,connection,sql,custId);
    }

    @Override
    public long getCount(Connection connection) {
        String sql = "select count(*) from customers";
        return getValue(long.class,connection,sql);
    }

    @Override
    public List<Customer> getAll(Connection connection) {
        String sql = "select cust_id, cust_name, email, birth from customers";
        return super.getInstanceList(connection,sql);
    }
}
