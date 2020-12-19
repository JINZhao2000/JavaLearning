package com.ayy.collections;

import org.junit.Test;

import java.util.*;

/**
 * @ ClassName IteratorTest
 * @ Description test of the use of Iterator
 * @ Author Zhao JIN
 * @ Date 30/10/2020 13:18
 * @ Version 1.0
 */
public class IteratorTest {
    /**
     * Iterator for ArrayList
     */
    @Test
    public void testIteratorList(){
        List<String> ls1 = new ArrayList<>();
        ls1.add("aa");
        ls1.add("bb");
        ls1.add("cc");
        ls1.add("dd");
        for (Iterator<String> iter = ls1.iterator(); iter.hasNext();) {
            String temp = iter.next();
            System.out.println(temp);
        }
    }

    /**
     * Iterator for HashSet
     */
    @Test
    public void testIteratorSet(){
        Set<String> ss1 = new HashSet<>();
        ss1.add("aa");
        ss1.add("bb");
        ss1.add("cc");
        ss1.add("dd");
        for (Iterator<String> iter = ss1.iterator();iter.hasNext();) {
            String temp = iter.next();
            System.out.println(temp);
        }
    }

    /**
     * Iterator for HashMap
     * use EntrySet
     */
    @Test
    public void testIteratorMap1(){
        Map<Integer,String> ms1 = new HashMap();
        ms1.put(1,"aa");
        ms1.put(2,"bb");
        ms1.put(3,"cc");
        ms1.put(4,"dd");
		Set<Map.Entry<Integer,String>> ss = ms1.entrySet();
		for (Iterator<Map.Entry<Integer,String>> iter = ss.iterator();iter.hasNext();){
			Map.Entry<Integer,String> temp = iter.next();
			System.out.println("key : " +temp.getKey() + "; value : " + temp.getValue());
		}
    }

    /**
     * Iterator for HashMap
     * use KeySet
     */
    @Test
    public void testIteratorMap2(){
        Map<Integer,String> ms1 = new HashMap();
        ms1.put(1,"aa");
        ms1.put(2,"bb");
        ms1.put(3,"cc");
        ms1.put(4,"dd");
        Set<Integer> keySet = ms1.keySet();
        for (Iterator<Integer> iter = keySet.iterator();iter.hasNext();){
            Integer key = iter.next();
            System.out.println("key : "+key+"; value : "+ms1.get(key));
        }
    }
}
