package com.ayy.MySQL.exo;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @ ClassName JDBCUtilsForAliyun
 * @ Description
 * @ Author Zhao JIN
 * @ Date 12/01/2021 22H
 * @ Version 1.0
 */
public class JDBCUtilsForAliyun {
    public static Connection getConnection(){
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("aliyun.properties");
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

    public static int update(String sql,Object... objects){
        if (JDBCUtilsForAliyun.getConnection() != null) {
            try (Connection connection = JDBCUtilsForAliyun.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
                if(objects.length==0){
                    preparedStatement.execute();
                    return 0;
                }
                int index = 1;
                for (Object o:objects) {
                    preparedStatement.setObject(index,o);
                    index++;
                }
                return preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return -1;
            }
        }
        return -1;
    }
}
