package com.ayy.MySQL.connectionpool.dbcp;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @ ClassName DBCPTest
 * @ Description
 * @ Author Zhao JIN
 * @ Date 17/01/2021 14H
 * @ Version 1.0
 */
public class DBCPTest {
    @Test
    public void testConnection1() throws SQLException {
        BasicDataSource source = new BasicDataSource();

        source.setDriverClassName("driverClassName");
        source.setUrl("url");
        source.setUsername("username");
        source.setPassword("password");

        Connection connection = source.getConnection();
        Assert.assertNotNull(connection);
    }

    @Test
    public void testConnection2() throws Exception {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
        Properties pros = new Properties();
        pros.load(is);
        BasicDataSource source = BasicDataSourceFactory.createDataSource(pros);
        Connection connection = source.getConnection();
        Assert.assertNotNull(connection);
    }
}
