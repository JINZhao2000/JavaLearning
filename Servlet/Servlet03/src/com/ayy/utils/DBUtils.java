package com.ayy.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 05/02/2021
 * @ Version 1.0
 */
public class DBUtils {
    public static DruidDataSource dataSource;
    public static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL = new ThreadLocal<>();

    static {
        Properties properties = new Properties();
        InputStream is = DBUtils.class.getClass().getClassLoader().getResourceAsStream("aliyun.properties");
        try {
            properties.load(is);
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtils.close(is);
        }
    }

    public static Connection getConnection() {
        Connection connection = CONNECTION_THREAD_LOCAL.get();
        try {
            if (connection == null) {
                connection = dataSource.getConnection();
                CONNECTION_THREAD_LOCAL.set(connection);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtils.close(connection);
        }
        return connection;
    }

    public static void begin(){
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtils.close(connection);
        }
    }

    public static void commit(){
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtils.close(connection);
        }
    }

    public static void rollback(){
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            connection.rollback();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtils.close(connection);
        }
    }

    public static void close(AutoCloseable... clos){
        for (AutoCloseable ac : clos){
            if(ac!=null){
                try {
                    ac.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (ac instanceof Connection){
                CONNECTION_THREAD_LOCAL.remove();
            }
        }
    }


}
