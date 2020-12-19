package com.ayy.classLoader;

import org.junit.Test;

/**
 * @ ClassName ClassLoaderTest
 * @ Description the test of ClassLoader
 * @ Author Zhao JIN
 * @ Date 30/10/2020 12:07
 * @ Version 1.0
 */
public class ClassLoaderTest {
    @Test
    public void test01(){
        Class c;
        ClassLoader cl;
        cl = ClassLoader.getSystemClassLoader();
        System.out.println(cl);
        while(cl!=null){
            System.out.println(cl);
            cl=cl.getParent();
        }
        try {
            c=Class.forName("java.lang.Object");
            cl = c.getClassLoader();
            System.out.println("java.lang.Object : "+cl);

            c=Class.forName("com.ayy.classLoader.ClassLoaderTest");
            cl = c.getClassLoader();
            System.out.println("com.ayy.classLoader.ClassLoaderTest : "+cl);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
