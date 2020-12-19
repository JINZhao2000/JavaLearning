package com.ayy.base;

import org.junit.Test;

import java.util.Random;

/**
 * @ ClassName RandomTest
 * @ Description use of the class Random
 * @ Author Zhao JIN
 * @ Date 30/10/2020 11:04
 * @ Version 1.0
 */
public class RandomTest {
    @Test
    public void test01() {
        Random rnd = new Random();
        //[0,1) double
        System.out.println(rnd.nextDouble());
        //int
        System.out.println(rnd.nextInt());
        //[0,1) float
        System.out.println(rnd.nextFloat());
        //false or true
        System.out.println(rnd.nextBoolean());
        //[0,10) int
        System.out.println(rnd.nextInt(10));
        //20+[0,1)*10 int
        System.out.println(20+(int)rnd.nextDouble()*10);
        System.out.println(20+rnd.nextInt(10));
    }
}
