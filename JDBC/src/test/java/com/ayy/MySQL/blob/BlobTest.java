package com.ayy.MySQL.blob;


import com.ayy.MySQL.util.JDBCUtilsForAliyun;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @ ClassName BlobTest
 * @ Description
 * @ Author Zhao JIN
 * @ Date 13/01/2021 23H
 * @ Version 1.0
 */
public class BlobTest {
    @Test
    public void testInsert(){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = JDBCUtilsForAliyun.getConnection();
            String sql = "insert into customers(cust_name,email,birth,photo)values(?,?,?,?)";
            assert connection != null;
            statement = connection.prepareStatement(sql);
            statement.setObject(1,"B");
            statement.setObject(2,"b@c.d");
            statement.setObject(3,"2000-1-2");
            String path = this.getClass().getClassLoader().getResource("test.jpg").toString();
            path = path.substring(path.indexOf("/")+1);
            statement.setBlob(4,new FileInputStream(new File(path)));
            statement.execute();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtilsForAliyun.close(statement,connection);
        }
    }
}
