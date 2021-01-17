package com.ayy.MySQL.connectionpool.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mysql.cj.jdbc.Driver;
import org.junit.Assert;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @ ClassName DruidTest
 * @ Description
 * @ Author Zhao JIN
 * @ Date 17/01/2021 17H
 * @ Version 1.0
 */
public class DruidTest {
    @Test
    public void TestConnection1() throws SQLException {
        DruidDataSource source = new DruidDataSource();
        source.setDriver(new Driver());
        source.setUrl("url");
        source.setUsername("username");
        source.setPassword("password");
        Connection connection = source.getConnection();
        Assert.assertNotNull(connection);
    }

    @Test
    public void TestConnection2() throws Exception {
        Properties pros = new Properties();
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
        pros.load(is);
        DataSource source = DruidDataSourceFactory.createDataSource(pros);
        Connection connection = source.getConnection();
        Assert.assertNotNull(connection);
    }
}
