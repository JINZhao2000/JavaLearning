package com.ayy.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 12/02/2021
 * @ Version 1.0
 */
public class DBUtils {
    private static DruidDataSource dataSource;
    private static final ThreadLocal<Connection> THREAD_LOCAL = new ThreadLocal<>();

    static {
        Properties prop = new Properties();
        InputStream is = DBUtils.class.getClassLoader().getResourceAsStream("aliyun.properties");
        try {
            prop.load(is);
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        connection = THREAD_LOCAL.get();
        try {
            if (connection == null) {
                connection = dataSource.getConnection();
                THREAD_LOCAL.set(connection);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    public static void beginTX(){
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void commit(){
        Connection connection = null;
        try {
            connection = getConnection();
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
            connection = getConnection();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtils.close(connection);
        }
    }

    public static void close(Connection connection, AutoCloseable... clos){
        if(connection!=null) {
            try {
                connection.close();
                THREAD_LOCAL.remove();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        for (AutoCloseable clo : clos) {
            if(clo!=null){
                try {
                    clo.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
