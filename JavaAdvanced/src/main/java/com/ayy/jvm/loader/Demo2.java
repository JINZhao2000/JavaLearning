package com.ayy.jvm.loader;

/**
 * @ ClassName Demo2
 * @ Description
 * @ Author Zhao JIN
 * @ Date 02/01/2021 22H
 * @ Version 1.0
 */
public class Demo2 {
    public static void main(String[] args) {
        System.out.println(ClassLoader.getSystemClassLoader());
        System.out.println(ClassLoader.getSystemClassLoader().getParent());
        System.out.println(ClassLoader.getSystemClassLoader().getParent().getParent());
        System.out.println(System.getProperty("java.class.path"));
        System.out.println(System.getProperty("java.ext.dirs"));
        System.out.println(System.getProperty("sun.boot.class.path"));
    }
}
