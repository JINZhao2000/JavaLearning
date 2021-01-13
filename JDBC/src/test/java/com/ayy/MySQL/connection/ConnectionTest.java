package com.ayy.MySQL.connection;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {
    @Test
    public void demo1() throws SQLException {
        //Driver driver = new OracleDriver();
        //String url = "jdbc:oracle:thin:@telline.univ-tlse3.fr:1521:etupre";
        //Connection connect = DriverManager.getConnection(url,"jnz3059a","*******");
        Driver driver = new com.mysql.cj.jdbc.Driver();
        String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","root");
        Connection connection = driver.connect(url,info);
        System.out.println(connection);
    }

    @Test
    public void demo02() throws Exception {
        Class<?> classDriver = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver) classDriver.getDeclaredConstructor().newInstance();

        String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","root");

        Connection connection = driver.connect(url,info);
        System.out.println(connection);
    }

    @Test
    public void demo3() throws Exception{
        Class<?> classDriver = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver) classDriver.getDeclaredConstructor().newInstance();

        DriverManager.registerDriver(driver);
        String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","root");

        Connection connection = DriverManager.getConnection(url,info);
        System.out.println(connection);
    }

    @Test
    public void demo04() throws Exception{
        String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","root");

        // 通过 Driver的静态代码块注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection(url, info);
        System.out.println(connection);
    }

    // decoupling the data and the code
    //
    @Test
    public void demo05() throws Exception{
        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("MySQLConnectionJDBC.properties");

        Properties pros = new Properties();
        pros.load(is);

        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String url = pros.getProperty("url");
        String driverClass = pros.getProperty("driverClass");

        Class.forName(driverClass);

        Connection connection = DriverManager.getConnection(url,user,password);
        System.out.println(connection);
    }
}
