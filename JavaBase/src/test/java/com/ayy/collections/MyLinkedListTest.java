package com.ayy.collections;

import org.junit.Test;

/**
 * @ ClassName MyLinkedListTest
 * @ Description
 * @ Author Zhao JIN
 * @ Date 30/10/2020 13:13
 * @ Version 1.0
 */
public class MyLinkedListTest {
    @Test
    public void test01(){
        MyLinkedList<String> mll = new MyLinkedList<>();
        mll.add("a");
        mll.add("b");
        mll.add("c");
        System.out.println(mll);
        System.out.println(mll.get(1));
        System.out.println(mll.get(2));
        mll.remove(2);
        mll.remove(0);
        System.out.println(mll);
        mll.add(1,"a");
        System.out.println(mll);
    }
}
