package com.ayy.javaIO.objectIOStream;

import org.junit.Test;

import java.io.*;

/**
 * @ ClassName ObjectIOTest
 * @ Description test for ObjectIOStream
 * @ Author Zhao JIN
 * @ Date 30/10/2020 15:32
 * @ Version 1.0
 */
public class ObjectIOTest {
    @Test
    public void test01(){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(baos));

            oos.writeObject("ObjectString");
            oos.writeObject(new Employee("A",1000));
            oos.flush();
            byte[] data = baos.toByteArray();
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new ByteArrayInputStream(data)));
            System.out.println(ois.readObject());
            System.out.println(ois.readObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
