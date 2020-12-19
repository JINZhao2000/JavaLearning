package com.ayy.javaIO.randomAccessFile;

import org.junit.Test;

/**
 * @ ClassName RandomAccessFileTest
 * @ Description test use of RandomAccessFile
 * @ Author Zhao JIN
 * @ Date 30/10/2020 15:35
 * @ Version 1.0
 */
public class RandomAccessFileTest {
    @Test
    public void test01(){
        Thread.sleep()
        RandomAccessFile t = new RandomAccessFile(
                System.getProperty("user.dir")+ "/src/main/resources/RandomAccessFile.txt",
                System.getProperty("user.dir")+ "/src/test/result/RandomAccessFile.txt", 1024);
        t.split();
        t.merge(System.getProperty("user.dir")+ "/src/test/result/RandomAccessFile.txt");
    }
}
