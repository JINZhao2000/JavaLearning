package com.ayy.exceptions;

import org.junit.Test;

/**
 * @ ClassName LostExceptionTest
 * @ Description test if the exception is lost
 * @ Author Zhao JIN
 * @ Date 30/10/2020 12:55
 * @ Version 1.0
 */
public class LostExceptionTest {
    @Test
    public void test01(){
        try {
            System.out.println(new WithReturn().methodB(1));
            System.out.println("No Exception");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
