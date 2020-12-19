package com.ayy.collections;

import java.util.HashMap;

/**
 * @ ClassName MyHashSet
 * @ Description MyHashSet
 * @ Author Zhao JIN
 * @ Date 30/10/2020 13:10
 * @ Version 1.0
 */
public class MyHashSet<E> {
    private HashMap map;
    private static final Object PRESENT = new Object();
    public MyHashSet (){
        map = new HashMap();
    }

    public int size(){
        return map.size();
    }

    public void add(E element){
        map.put(element,PRESENT);
    }

    public String toString(){
        return map.keySet().toString();
    }
}
