package com.ayy.MySQL.dao2;

import com.ayy.MySQL.exo.JDBCUtilsForAliyun;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ ClassName BaseDAO
 * @ Description
 * @ Author Zhao JIN
 * @ Date 15/01/2021 22H
 * @ Version 1.0
 */
public abstract class BaseDAO<T> {
    private final Class<T> clazz;

    {
        Type genericSuperClass = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperClass;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        clazz = CastUtils.cast(actualTypeArguments[0]);
    }

    public int update(Connection connection, String sql, Object... objects) {
        PreparedStatement preparedStatement = null;
        try {
            assert connection != null;
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

    public List<T> getInstanceList(Connection connection, String sql, Object... objects) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
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

                    T t = clazz.getDeclaredConstructor().newInstance();
                    for (int i = 0; i < columnCount; i++) {
                        Object columnValue = resultSet.getObject(i + 1);
                        String columeLabel = resultSetMetaData.getColumnLabel(i + 1);
                        Field field = clazz.getDeclaredField(columeLabel);
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

    public T getInstance(Connection connection, String sql, Object... objects) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < objects.length; i++) {
                preparedStatement.setObject(i + 1, objects[i]);
            }
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            if (resultSet.next()) {
                T t = clazz.getDeclaredConstructor().newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object object = resultSet.getObject(i + 1);
                    String columnName = resultSetMetaData.getColumnName(i + 1);
                    Field field = clazz.getDeclaredField(columnName); // reflect field
                    field.setAccessible(true); // private -> accessible
                    field.set(t, object);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtilsForAliyun.close(preparedStatement, resultSet);
        }
        return null;
    }

    public <E> E getValue(Class<E> clazz,Connection connection, String sql, Object... args) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                return CastUtils.cast(resultSet.getObject(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtilsForAliyun.close(resultSet,statement);
        }
        return null;
    }
}
