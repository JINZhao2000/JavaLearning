package com.ayy.collections;

import org.junit.Test;

/**
 * @ ClassName MyHashMapTest
 * @ Description test of MyHashMap
 * @ Author Zhao JIN
 * @ Date 30/10/2020 13:09
 * @ Version 1.0
 */
public class MyHashMapTest {
    @Test
    public void test01(){
        MyHashMap<Integer,String> mhp = new MyHashMap<>();
        mhp.put(1, "aa");
        mhp.put(2, "bb");
        mhp.put(3, "cc");
        mhp.put(3, "dd");
        mhp.put(17, "ee");
        System.out.println(mhp.toString());
        System.out.println(mhp.get(17));
    }
}
