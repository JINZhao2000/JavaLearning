package com.ayy.base;

import org.junit.Test;

/**
 * @ ClassName Math
 * @ Description use of the class Math
 * @ Author Zhao JIN
 * @ Date 30/10/2020 11:24
 * @ Version 1.0
 */
public class MathTest {
    @Test
    public void test01(){
        // Rounding
        System.out.println(Math.ceil(3.2));
        System.out.println(Math.floor(3.2));
        System.out.println(Math.round(3.2));
        System.out.println(Math.round(3.8));
        // Absolute value
        System.out.println(Math.abs(-45));
        // Square root
        System.out.println(Math.sqrt(64));
        // a to the power of b
        System.out.println(Math.pow(5,2));
        System.out.println(Math.pow(2,5));
        // constant pi e
        System.out.println(Math.PI);
        System.out.println(Math.E);
        // random value
        System.out.println(Math.random());//[0,1)
    }
}
