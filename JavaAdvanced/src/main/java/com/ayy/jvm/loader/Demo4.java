package com.ayy.jvm.loader;

/**
 * @ ClassName Demo4
 * @ Description
 * @ Author Zhao JIN
 * @ Date 04/01/2021 20H
 * @ Version 1.0
 */
public class Demo4 {
    public static void main(String[] args) throws Exception {
        //FileSystemClassLoader classLoader = new FileSystemClassLoader("D:/JavassistTest/temp");
        DechiffreClassLoader classLoader = new DechiffreClassLoader("D:/JavassistTest/temp");
        System.out.println("--- c ---");
        Class<?> c = classLoader.findClass("com.ayy.ref.uselib.A");
    }
}
