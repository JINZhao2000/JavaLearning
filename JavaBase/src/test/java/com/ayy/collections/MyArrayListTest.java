package com.ayy.collections;

import org.junit.Test;

/**
 * @ ClassName MyArrayListTest
 * @ Description test of MyArrayList
 * @ Author Zhao JIN
 * @ Date 30/10/2020 13:08
 * @ Version 1.0
 */
public class MyArrayListTest {
    @Test
    public void test01(){
        MyArrayList m1 = new MyArrayList(20);
        for (int i = 0; i <30 ; i++) {
            m1.add("a");
            m1.add("b");
        }
        m1.set("c",10);
        System.out.println(m1);
        System.out.println(m1.get(10));
    }
}
