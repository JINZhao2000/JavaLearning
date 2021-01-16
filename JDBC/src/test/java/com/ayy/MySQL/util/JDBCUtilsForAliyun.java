package com.ayy.MySQL.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @ ClassName JDBCUtilsForAliyun
 * @ Description
 * @ Author Zhao JIN
 * @ Date 12/01/2021 22H
 * @ Version 1.0
 */
public class JDBCUtilsForAliyun {
    public static Connection getConnection() {
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
        }finally {
            JDBCUtilsForAliyun.close(is);
        }
        return null;
    }

    public static void close(AutoCloseable... clos) {
        for (AutoCloseable clo : clos) {
            try {
                if (clo != null) {
                    clo.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int update(String sql, Object... objects) {
        if (JDBCUtilsForAliyun.getConnection() != null) {
            try (Connection connection = JDBCUtilsForAliyun.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
                if (objects.length == 0) {
                    preparedStatement.execute();
                    return 0;
                }
                int index = 1;
                for (Object o : objects) {
                    preparedStatement.setObject(index, o);
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

    public static int updateV2(Connection conn, String sql, Object... objects) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = conn;
            assert connection != null;
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            if (objects.length == 0) {
                preparedStatement.execute();
                return 0;
            }
            int index = 1;
            for (Object o : objects) {
                preparedStatement.setObject(index, o);
                index++;
            }
            return preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        } finally {
            JDBCUtilsForAliyun.close(preparedStatement);
        }
    }

    public static <T> List<T> getInstance(Connection conn, Class<T> tClass, String sql, Object... objects) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = conn;
            assert connection != null;
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtilsForAliyun.close(preparedStatement, resultSet);
        }
        return null;
    }

    private static ComboPooledDataSource dataSource = new ComboPooledDataSource("intergalactoApp");

    public static Connection getConnectionByC3P0() throws SQLException, PropertyVetoException {
        return dataSource.getConnection();
    }
}
