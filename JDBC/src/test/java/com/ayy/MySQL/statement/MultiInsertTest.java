package com.ayy.MySQL.statement;

import com.ayy.MySQL.util.JDBCUtilsForAliyun;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;

/**
 * @ ClassName MultiInsertTest
 * @ Description
 * @ Author Zhao JIN
 * @ Date 14/01/2021 22H
 * @ Version 1.0
 */

/**
 * CREATE TABLE goods(
 *  gid INT AUTO_INCREMENT PRIMARY KEY,
 *  NAME VARCHAR(25)
 * );
 */
public class MultiInsertTest {

    // 111810 ms
    @Test
    public void test01(){
        Connection connection = null;
        Statement statement = null;
        try{
            connection = JDBCUtilsForAliyun.getConnection();
            assert connection!=null;
            statement = connection.createStatement();
            long start = System.currentTimeMillis();
            for (int i = 0; i < 2000; i++) {
                String sql = "insert into goods(name)values('name_"+i+"')";
                statement.execute(sql);
            }
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtilsForAliyun.close(statement,connection);
        }
    }
}
