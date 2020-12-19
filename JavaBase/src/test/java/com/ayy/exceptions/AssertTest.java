package com.ayy.exceptions;

import org.junit.Test;

/**
 * @ ClassName AssertTest
 * @ Description test the use of Assert
 * @ Author Zhao JIN
 * @ Date 30/10/2020 12:23
 * @ Version 1.0
 */
public class AssertTest {
    @Test
    /**
     * VM Options -enableassertions / -ea is needed
     */
    public void test01(){
        System.out.println(divide(1,2));
        System.out.println(divide(1,0));
    }

    public static int divide(int a,int b){
        assert b!=0:"Le diviseur est nul";
        return a/b;
    }
}
