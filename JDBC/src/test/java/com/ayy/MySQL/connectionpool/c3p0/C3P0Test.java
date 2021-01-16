package com.ayy.MySQL.connectionpool.c3p0;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import org.junit.Assert;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @ ClassName C3P0Test
 * @ Description
 * @ Author Zhao JIN
 * @ Date 16/01/2021 22H
 * @ Version 1.0
 */
public class C3P0Test {
    @Test
    public void testConnection1() throws SQLException, PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("driverClass");
        dataSource.setJdbcUrl("url");
        dataSource.setUser("user");
        dataSource.setPassword("password");
        dataSource.setInitialPoolSize(10);
        Connection connection = dataSource.getConnection();
        Assert.assertNotNull(connection);
        connection.close();
        DataSources.destroy(dataSource);
    }

    @Test
    public void testConnection2() throws SQLException, PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource("intergalactoApp");
        Connection connection = dataSource.getConnection();
        Assert.assertNotNull(connection);
    }
}
