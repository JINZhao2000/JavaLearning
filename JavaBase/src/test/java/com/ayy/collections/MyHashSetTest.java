package com.ayy.collections;

import org.junit.Test;

/**
 * @ ClassName MyHashSetTest
 * @ Description test of MyHashSet
 * @ Author Zhao JIN
 * @ Date 30/10/2020 13:11
 * @ Version 1.0
 */
public class MyHashSetTest {
    @Test
    public void test01(){
        MyHashSet<String> mhs = new MyHashSet<>();
        mhs.add("aa");
        mhs.add("bb");
        mhs.add("cc");
        mhs.add("dd");
        System.out.println(mhs);
    }
}
