package com.ayy.collections;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ ClassName CollectionsUtilTest
 * @ Description test of the util class Collections
 * @ Author Zhao JIN
 * @ Date 30/10/2020 13:17
 * @ Version 1.0
 */
public class CollectionsUtilTest {
    @Test
    public void test01(){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("num:"+i);
        }
        System.out.println(list);

        Collections.reverse(list);
        System.out.println(list);

        Collections.shuffle(list);
        System.out.println(list);

        Collections.sort(list);//implements Comparable<T>
        System.out.println(list);

        System.out.println(Collections.binarySearch(list,"num:4"));
    }
}
