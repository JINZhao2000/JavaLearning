package com.ayy.MySQL.blob;


import com.ayy.MySQL.util.JDBCUtilsForAliyun;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;

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

    @Test
    public void testQuery(){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            connection = JDBCUtilsForAliyun.getConnection();
            String sql = "select cust_id,cust_name,email,birth,photo from customers where cust_id = ?";
            assert connection != null;
            statement = connection.prepareStatement(sql);
            statement.setObject(1,"2");
            result = statement.executeQuery();
            if(result.next()){
//                int id = result.getInt(1);
//                String name = result.getString(2);
//                String email = result.getString(3);
//                Date birth = result.getDate(4);
                int id = result.getInt("cust_id");
                String name = result.getString("cust_name");
                String email = result.getString("email");
                Date birth = result.getDate("birth");
                Customer cust = new Customer(id,name,email,birth);
                System.out.println("----------");
                System.out.println(cust);
                Blob blob = result.getBlob("photo");
                is = blob.getBinaryStream();
                fos = new FileOutputStream(new File("get.jpg"));
                byte[] buffer = new byte[1024*1024*16];
                int len = -1;
                while((len = is.read(buffer))!=-1){
                    fos.write(buffer,0,len);
                    fos.flush();
                }
                System.out.println("----------");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtilsForAliyun.close(statement,connection,result,fos,is);
        }
    }
}
