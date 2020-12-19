package com.ayy.javaIO.file;

import org.junit.Test;

/**
 * @ ClassName FileCountTest
 * @ Description test for class FileCount
 * @ Author Zhao JIN
 * @ Date 30/10/2020 14:17
 * @ Version 1.0
 */
public class FileCountTest {
    @Test
    public void test01(){
        FileCount dc = new FileCount(".src/main");
        System.out.println(dc);

        FileCount dc2 = new FileCount(".src/test");
        System.out.println(dc2);
    }
}
