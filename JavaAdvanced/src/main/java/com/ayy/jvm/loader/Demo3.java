package com.ayy.jvm.loader;

import java.lang.reflect.Method;

/**
 * @ ClassName Demo3
 * @ Description
 * @ Author Zhao JIN
 * @ Date 03/01/2021 20H
 * @ Version 1.0
 */
public class Demo3 {
    public static void main(String[] args) throws Exception {
        FileSystemClassLoader classLoader = new FileSystemClassLoader("D:/JavassistTest");
        System.out.println("--- c1 ---");
        Class<?> c = classLoader.findClass("com.ayy.ref.uselib.Emp");
        Object o = c.getDeclaredConstructor().newInstance();
        Method m = c.getMethod("test",int.class);
        m.invoke(o,1);
        System.out.println(c.getClassLoader());
        System.out.println("--- c2 ---");
        Class<?> c2 = classLoader.findClass("java.lang.String");
        System.out.println(c2.getClassLoader());
        System.out.println("--- c3 ---");
        Class<?> c3 = classLoader.findClass("com.ayy.ref.uselib.A");
        System.out.println(c3.getClassLoader());
    }
}
