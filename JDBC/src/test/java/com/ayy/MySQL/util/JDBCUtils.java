package com.ayy.MySQL.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Utils class
 */
public class JDBCUtils {
    public static Connection getConnection(){
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("MySQLConnectionJDBC.properties");
        Properties pros = new Properties();
        try {
            pros.load(is);
            String user = pros.getProperty("user");
            String password = pros.getProperty("password");
            String url = pros.getProperty("url");
            String driverClass = pros.getProperty("driverClass");
            Class.forName(driverClass);
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static void close(AutoCloseable... clos){
        for (AutoCloseable clo:clos) {
            try {
                if(clo!=null) {
                    clo.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
