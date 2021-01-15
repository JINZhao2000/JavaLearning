package com.ayy.MySQL.preparedStatement;

import com.ayy.MySQL.util.JDBCUtilsForAliyun;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @ ClassName MultiInsertTest
 * @ Description
 * @ Author Zhao JIN
 * @ Date 14/01/2021 22H
 * @ Version 1.0
 *
 * CREATE TABLE goods(
 *  gid INT AUTO_INCREMENT PRIMARY KEY,
 *  NAME VARCHAR(25)
 * );
 */
public class MultiInsertTest {

    // 111256 ms
    @Test
    public void test01(){
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = JDBCUtilsForAliyun.getConnection();
            assert connection!=null;
            String sql = "insert into goods(name)values(?)";
            statement = connection.prepareStatement(sql);
            long start = System.currentTimeMillis();
            for (int i = 0; i < 2000; i++) {
                statement.setObject(1,"name_"+i);
                statement.execute();
            }
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtilsForAliyun.close(statement,connection);
        }
    }

    // 436 ms
    // ?rewriteBatchedStatements=true
    @Test
    public void test02(){
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = JDBCUtilsForAliyun.getConnection();
            assert connection!=null;
            String sql = "insert into goods(name)values(?)";
            statement = connection.prepareStatement(sql);
            long start = System.currentTimeMillis();
            for (int i = 0; i < 2000; i++) {
                statement.setObject(1,"name_"+i);
                statement.addBatch();
                if((i+1)%500==0){
                    statement.executeBatch();
                    statement.clearBatch();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtilsForAliyun.close(statement,connection);
        }
    }

    // 27709 ms
    @Test
    public void test03(){
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = JDBCUtilsForAliyun.getConnection();
            assert connection!=null;
            String sql = "insert into goods(name)values(?)";
            statement = connection.prepareStatement(sql);
            long start = System.currentTimeMillis();
            for (int i = 0; i < 200000; i++) {
                statement.setObject(1,"name_"+i);
                statement.addBatch();
                if((i+1)%500==0){
                    statement.executeBatch();
                    statement.clearBatch();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtilsForAliyun.close(statement,connection);
        }
    }

    // 27751 ms
    @Test
    public void test04(){
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = JDBCUtilsForAliyun.getConnection();
            assert connection!=null;
            connection.setAutoCommit(false);
            String sql = "insert into goods(name)values(?)";
            statement = connection.prepareStatement(sql);
            long start = System.currentTimeMillis();
            for (int i = 0; i < 200000; i++) {
                statement.setObject(1,"name_"+i);
                statement.addBatch();
                if((i+1)%500==0){
                    statement.executeBatch();
                    statement.clearBatch();
                }
            }
            connection.commit();
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtilsForAliyun.close(statement,connection);
        }
    }
}
