package com.ayy.MySQL.preparedStatement;

import com.ayy.MySQL.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class PreparedStatementSelectTest {
    @Test
    public void testSelect () {
        String sql = "SELECT cust_id,cust_name FROM customers WHERE cust_id = ? ";
        System.out.println(select(sql,1));
    }

    public Customer select (String sql, Object... objects) {
        try {
            Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < objects.length; i++) {
                preparedStatement.setObject(i + 1, objects[i]);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            if (resultSet != null) {
                resultSet.next();
                Customer customer = new Customer();
                for (int i = 0; i < columnCount; i++) {
                    Object object = resultSet.getObject(i + 1);
                    String columnName = resultSetMetaData.getColumnName(i + 1);
                    Field field = Customer.class.getDeclaredField(columnName); // reflect field
                    field.setAccessible(true); // private -> accessible
                    field.set(customer, object);
                }
                return customer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
