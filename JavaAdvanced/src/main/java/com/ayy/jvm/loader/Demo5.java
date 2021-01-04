package com.ayy.jvm.loader;

/**
 * @ ClassName Demo5
 * @ Description
 * @ Author Zhao JIN
 * @ Date 04/01/2021 21H
 * @ Version 1.0
 */
public class Demo5 {
    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader classLoader = Demo5.class.getClassLoader();
        System.out.println(classLoader);

        ClassLoader classLoader2 = Thread.currentThread().getContextClassLoader();
        System.out.println(classLoader2);

        Thread.currentThread().setContextClassLoader(new FileSystemClassLoader("D:/JavassistTest"));
        System.out.println(Thread.currentThread().getContextClassLoader());

        Class<?> c = Thread.currentThread().getContextClassLoader().loadClass("com.ayy.ref.uselib.A");
        System.out.println(c);
    }
}
