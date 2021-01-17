package com.ayy.MySQL.dbutils;

import com.ayy.MySQL.util.JDBCUtilsForAliyun;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @ ClassName QueryRunnerTest
 * @ Description
 * @ Author Zhao JIN
 * @ Date 17/01/2021 18H
 * @ Version 1.0
 */
public class QueryRunnerTest {
    @Test
    public void updateTest() {
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtilsForAliyun.getConnectionByDruid();
            String sql = "insert into goods(name) values(?)";
            int insertCount = runner.update(connection,sql,"Good1");
            Assert.assertEquals(1,insertCount);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtilsForAliyun.close(connection);
        }
    }

    @Test
    public void queryTest1() {
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtilsForAliyun.getConnectionByDruid();
            String sql = "select gid,name from goods where gid = ?";
            ResultSetHandler<Good> handler = new BeanHandler<>(Good.class);
            Good good = runner.query(connection,sql,handler,1);
            Assert.assertNotNull(good);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtilsForAliyun.close(connection);
        }
    }

    @Test
    public void queryTest2() {
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtilsForAliyun.getConnectionByDruid();
            String sql = "select gid,name from goods";
            BeanListHandler<Good> handler = new BeanListHandler<>(Good.class);
            List<Good> goods = runner.query(connection,sql,handler);
            Assert.assertNotNull(goods);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtilsForAliyun.close(connection);
        }
    }

    @Test
    public void queryTest3() {
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtilsForAliyun.getConnectionByDruid();
            String sql = "select gid,name from goods";
            MapHandler handler = new MapHandler();
            Map<String, Object> good = runner.query(connection,sql,handler);
            Assert.assertNotNull(good);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtilsForAliyun.close(connection);
        }
    }

    @Test
    public void queryTest4() {
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtilsForAliyun.getConnectionByDruid();
            String sql = "select gid,name from goods";
            MapListHandler handler = new MapListHandler();
            List<Map<String, Object>> goods = runner.query(connection,sql,handler);
            Assert.assertNotNull(goods);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtilsForAliyun.close(connection);
        }
    }

    @Test
    public void queryTest5() {
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtilsForAliyun.getConnectionByDruid();
            String sql = "select count(*) from goods";
            ScalarHandler handler = new ScalarHandler();
            Object value = runner.query(connection,sql,handler);
            System.out.println(value);
            Assert.assertNotNull(value);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtilsForAliyun.close(connection);
        }
    }

    @Test
    public void queryTest6() {
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtilsForAliyun.getConnectionByDruid();
            String sql = "select gid, name from goods where gid = ?";
            ResultSetHandler<Good> handler = new ResultSetHandler<Good>() {
                @Override
                public Good handle(ResultSet rs) throws SQLException {
                    return new Good(1,"MyGood"); // self-defined result
                }
            };
            Object value = runner.query(connection,sql,handler,1);
            Assert.assertNotNull(value);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DbUtils.closeQuietly(connection);
        }
    }
}
