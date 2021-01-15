package com.ayy.MySQL.transaction;

import com.ayy.MySQL.util.JDBCUtilsForAliyun;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @ ClassName TransactionTest
 * @ Description
 * @ Author Zhao JIN
 * @ Date 15/01/2021 20H
 * @ Version 1.0
 *
 *
 * predefined :
 * CREATE TABLE user_table(
 *   uid INT AUTO_INCREMENT PRIMARY KEY,
 *   uname VARCHAR(20),
 *   balance INT DEFAULT 1000
 * );
 * INSERT INTO user_table(uname) VALUES ('AA');
 * INSERT INTO user_table(uname) VALUES ('BB');
 * INSERT INTO user_table(uname) VALUES ('CC');
 * INSERT INTO user_table(uname) VALUES ('DD');
 */
public class TransactionTest {

    /**
     * update user_table set balance = balance - 100 where uname = 'AA';
     * update user_table set balance = balance + 100 where uname = 'BB';
     */
    @Test
    public void testUpdate01(){
        String out = "update user_table set balance = balance - 100 where uname = ?";
        JDBCUtilsForAliyun.update(out,"AA");
        String in = "update user_table set balance = balance + 100 where uname = ?";
        JDBCUtilsForAliyun.update(in,"BB");
        System.out.println("Success");
    }

    /**
     * Exception
     */
    @Test
    public void testUpdate02(){
        String out = "update user_table set balance = balance - 100 where uname = ?";
        JDBCUtilsForAliyun.update(out,"AA");

        System.out.println(1/0);

        String in = "update user_table set balance = balance + 100 where uname = ?";
        JDBCUtilsForAliyun.update(in,"BB");
        System.out.println("Success");
    }

    @Test
    public void testUpdate03(){
        Connection connection = null;
        PreparedStatement outStatement = null;
        PreparedStatement inStatement = null;
        try {
            connection = JDBCUtilsForAliyun.getConnection();
            connection.setAutoCommit(false);
            String out = "update user_table set balance = balance - 100 where uname = ?";
            String in = "update user_table set balance = balance + 100 where uname = ?";
            outStatement = connection.prepareStatement(out);
            outStatement.setObject(1,"AA");
            inStatement = connection.prepareStatement(in);
            inStatement.setObject(1,"BB");
            outStatement.execute();

            System.out.println(1/0);

            inStatement.execute();
            System.out.println("Success");
        }catch (Exception e){
            try {
                connection.rollback();
                System.out.println("Failed");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            JDBCUtilsForAliyun.close(outStatement,inStatement,connection);
        }
    }

    @Test
    public void testTransaction1() throws SQLException, InterruptedException {
        final Connection connection1 = JDBCUtilsForAliyun.getConnection();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    Connection connection = connection1;
                    connection.setAutoCommit(false);
                    connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                    String sql = "select uid,uname,balance from user_table where uname = ?";
                    List<User> user = JDBCUtilsForAliyun.getInstance(connection, User.class, sql, "CC");
                    user.forEach(System.out::println);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        };
        Thread t = new Thread(()->{
            try {
                Connection connection = JDBCUtilsForAliyun.getConnection();
                connection.setAutoCommit(false);
                connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                String sql = "update user_table set balance = ? where uname = ?";
                JDBCUtilsForAliyun.updateV2(connection,sql,2000,"CC");
                Thread.sleep(15000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t1.start();
        t1.join();
        Thread.sleep(1000);
        t.start();
        Thread.sleep(1000);
        t2.start();
        t2.join();
    }
}
