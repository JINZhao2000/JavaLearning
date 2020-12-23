package com.ayy.ref.clazz;

import com.ayy.ref.clazz.bean.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @ ClassName Demo3
 * @ Description
 * @ Author Zhao JIN
 * @ Date 20/12/2020 11H
 * @ Version 1.0
 */
@SuppressWarnings("all")
public class Demo3 {
    public static void main(String[] args) throws Exception{
        String path = "com.ayy.ref.clazz.bean.User";
        Class<User> c = (Class<User>) Class.forName(path);
        User user1 = c.newInstance();
        User user2 = c.getDeclaredConstructor().newInstance();
        Method m = c.getDeclaredMethod("setUsername", String.class);
        m.invoke(user2,"B");
        Field f = c.getDeclaredField("age");
        f.setAccessible(true);
        f.setInt(user2,15);
        System.out.println(user2);
        Constructor<User> cu = c.getDeclaredConstructor(int.class, String.class, int.class);
        System.out.println(cu.newInstance(1000, "A", 18));
    }
}
