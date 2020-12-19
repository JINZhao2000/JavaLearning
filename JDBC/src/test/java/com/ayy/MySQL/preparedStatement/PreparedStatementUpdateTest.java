package com.ayy.MySQL.preparedStatement;

import com.ayy.MySQL.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * use PreparedStatement to replace Statement
 *
 * Insert Delete Update
 */
public class PreparedStatementUpdateTest {

    @Test
    public void testInsert () {
        String sql = "insert into customers(cust_id,cust_name,cust_address,cust_email) values(?,?,?,?)";
        update(sql,1,"Herve Leblanc","113 Route de Narbonne","herve.leblanc@iut-tlse3.fr");
        update(sql,2,"Herve Leblanc2","113 Route de Narbonne","herve.leblanc@iut-tlse3.fr");
        update(sql,3,"Herve Leblanc4","113 Route de Narbonne","herve.leblanc@iut-tlse3.fr");
    }

    @Test
    public void testUpdate () {
        // update `order` set ....
        // order is a key word in sql
        String sql = "update customers set cust_name = ? where cust_id = ?";
        update(sql, "Herve Leblanc3",3);
    }

    @Test
    public void testDelete () {
        String sql = "delete from customers where cust_id = ?";
        update(sql,3);
    }

    public void update(String sql,Object... objects){
        if (JDBCUtils.getConnection() != null) {
            try (Connection connection = JDBCUtils.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
                int index = 1;
                for (Object o:objects) {
                    preparedStatement.setObject(index,o);
                    index++;
                }
                preparedStatement.execute();
                System.out.println("Update success");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
