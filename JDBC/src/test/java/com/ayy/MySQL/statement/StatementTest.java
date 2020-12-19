package com.ayy.MySQL.statement;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Properties;

import static org.junit.Assert.assertNotNull;

public class StatementTest {
    @Test
    public void testLogin(){
        String username = "AA";
        String password = "aa";

        String sql = "SELECT user,password FROM user_table WHERE user='"+username+"'AND password ='"+password+"';";
        User user = get(sql,User.class);
        assertNotNull(user);
    }
    @Test
    public void testLogin2(){
        String username = "1' or ";
        String password = "=1 or '1' = '1";

        String sql = "SELECT user,password FROM user_table WHERE user='"+username+"'AND password ='"+password+"';";
        User user = get(sql,User.class);
        assertNotNull(user);
    }

    public <T> T get(String sql,Class<T> tClass){
        T t;

        InputStream is = StatementTest.class.getClassLoader().getResourceAsStream("MySQLConnectionJDBC.properties");

        Properties pros = new Properties();
        try {
            pros.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String url = pros.getProperty("url");
        String driverClass = pros.getProperty("driverClass");

        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        try (Connection connection = DriverManager.getConnection(url,user,password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)){
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columeCount = resultSetMetaData.getColumnCount();
            if(resultSet.next()){
                t = tClass.getDeclaredConstructor().newInstance();
                for (int i = 0; i < columeCount; i++) {
                    String columeName = resultSetMetaData.getColumnLabel(i+1);
                    Object columeVal = resultSet.getObject(columeName);
                    Field field = tClass.getDeclaredField(columeName);
                    field.setAccessible(true);
                    field.set(t,columeVal);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
