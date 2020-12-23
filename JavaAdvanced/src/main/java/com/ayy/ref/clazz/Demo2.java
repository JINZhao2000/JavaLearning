package com.ayy.ref.clazz;

/**
 * @ ClassName Demo2
 * @ Description
 * @ Author Zhao JIN
 * @ Date 20/12/2020 11H
 * @ Version 1.0
 */
public class Demo2 {
    public static void main(String[] args) throws ClassNotFoundException {
        String path = "com.ayy.ref.clazz.bean.User";
        Class<?> c = Class.forName(path);
        System.out.println(c.getName());
        System.out.println(c.getSimpleName());
        System.out.println(c.getFields().length);
        System.out.println(c.getDeclaredFields().length);
        System.out.println(c.getDeclaredMethods().length);
        System.out.println(c.getDeclaredConstructors().length);
    }
}
