package com.ayy.MySQL.preparedStatement;

import com.ayy.MySQL.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @ ClassName PreparedStatementQueryTest
 * @ Description
 * @ Author Zhao JIN
 * @ Date 29/10/2020 23:43
 * @ Version 1.0
 */
public class PreparedStatementQueryTest {
    @Test
    public void test01 () {
        String sql = "SELECT * FROM customers";
        List<Customer> customers = getInstance(Customer.class, sql);
        if(customers!=null) {
            customers.forEach(System.out::println);
            //customers.forEach((customer)-> System.out.println(customer));
        }
    }

    public <T> List<T> getInstance (Class<T> tClass, String sql, Object... objects) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            if (connection != null) {
                preparedStatement = connection.prepareStatement(sql);
                for (int i = 0; i < objects.length; i++) {
                    preparedStatement.setObject(i + 1, objects[i]);
                }
                resultSet = preparedStatement.executeQuery();
                if (resultSet != null) {
                    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                    int columnCount = resultSetMetaData.getColumnCount();
                    List<T> listResult = new ArrayList<>();
                    while (resultSet.next()) {
                        T t = tClass.getDeclaredConstructor().newInstance();
                        for (int i = 0; i < columnCount; i++) {
                            Object columnValue = resultSet.getObject(i + 1);
                            String columeLabel = resultSetMetaData.getColumnLabel(i + 1);
                            Field field = tClass.getDeclaredField(columeLabel);
                            field.setAccessible(true);
                            field.set(t, columnValue);
                        }
                        listResult.add(t);
                    }
                    return listResult;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(connection, preparedStatement, resultSet);
        }
        return null;
    }
}
