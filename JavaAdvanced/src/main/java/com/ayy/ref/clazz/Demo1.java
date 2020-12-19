package com.ayy.ref.clazz;

/**
 * @ ClassName Demo1
 * @ Description
 * @ Author Zhao JIN
 * @ Date 19/12/2020 21H
 * @ Version 1.0
 */
public class Demo1 {
    public static void main(String[] args) throws ClassNotFoundException {
        String path = "com.ayy.ref.clazz.bean.User";
        Class<?> c = Class.forName(path);
        System.out.println(c);
        System.out.println(String.class);
        System.out.println(path.getClass());
        System.out.println(int.class);
        System.out.println(int[].class);
        System.out.println(int[][].class);
    }
}
