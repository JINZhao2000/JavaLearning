package com.ayy.MySQL.preparedStatement;

import com.ayy.MySQL.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * @ClassName OrderForQueryTest
 * @Description getColumnLabel() -> The fields of object mapped and the fields in table are different
 * @Author Zhao JIN
 * @Date 29/10/2020 22:53
 * @Version 1.0
 */
public class OrderForQueryTest {

    @Test
    public void orderForQuery(){
        String sql = "select order_num orderId, order_date orderDate from orders where order_num = ?";
        System.out.println(orderForQuery(sql, 1));
    }

    public Order orderForQuery(String sql, Object... objects){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < objects.length; i++) {
                preparedStatement.setObject(i+1,objects[i]);
            }
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            if(resultSet!=null){
                Order order = new Order();
                resultSet.next();
                for (int i = 0; i < columnCount; i++) {
                    Object object = resultSet.getObject(i + 1);
                    // Label -> Name given in select
                    String columnLabel = resultSetMetaData.getColumnLabel(i+1);
                    Field field = Order.class.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(order,object);
                }
                return order;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.close(connection,preparedStatement,resultSet);
        }
        return null;
    }
}
