package com.ayy.MySQL.dao2;

import com.ayy.MySQL.util.JDBCUtilsForAliyun;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Random;

/**
 * @ ClassName CustomerDAOImplTest
 * @ Description
 * @ Author Zhao JIN
 * @ Date 15/01/2021 22H
 * @ Version 1.0
 */
public class CustomerDAOImplTest {
    private final CustomerDAO customerDAO = new CustomerDAOImpl();
    private final Connection connection = JDBCUtilsForAliyun.getConnection();

    @Test
    public void testInsert(){
        boolean flag = true;
        Customer cust = new Customer(3,"C","c@d.e",new Date(1000000000L));
        customerDAO.insert(connection,cust);
        flag = false;
        Assert.assertFalse(flag);
    }

    @Test
    public void testDeleteById(){
        boolean flag = true;
        customerDAO.deleteById(connection,3);
        flag = false;
        Assert.assertFalse(flag);
    }

    @Test
    public void testUpdate(){
        Random r = new Random();
        boolean flag = true;
        Customer cust = new Customer(2,"B","b@c.d",new Date(r.nextLong()>>16));
        customerDAO.update(connection,cust);
        flag = false;
        Assert.assertFalse(flag);
    }

    @Test
    public void testGetCustomerById(){
        Customer c = customerDAO.getCustomerById(connection,1);
        Assert.assertEquals(1, c.getCust_id());
        Assert.assertEquals("A", c.getCust_name());
        Assert.assertEquals("a@b.c", c.getEmail());
        Assert.assertEquals(2000-1900, c.getBirth().getYear());
        Assert.assertEquals(0, c.getBirth().getMonth());
        Assert.assertEquals(1, c.getBirth().getDate());
    }

    @Test
    public void testGetBirthById(){
        Date d = customerDAO.getBirthById(connection,1);
        Assert.assertEquals(2000-1900, d.getYear());
        Assert.assertEquals(0, d.getMonth());
        Assert.assertEquals(1, d.getDate());
    }

    @Test
    public void testGetCount(){
        long count = customerDAO.getCount(connection);
        Assert.assertEquals(2L,count);
    }

    @Test
    public void testGetAll(){
        List<Customer> customers = customerDAO.getAll(connection);
        Customer c = customers.get(0);
        Assert.assertEquals(1, c.getCust_id());
        Assert.assertEquals("A", c.getCust_name());
        Assert.assertEquals("a@b.c", c.getEmail());
        Assert.assertEquals(2000-1900, c.getBirth().getYear());
        Assert.assertEquals(0, c.getBirth().getMonth());
        Assert.assertEquals(1, c.getBirth().getDate());
    }
}
