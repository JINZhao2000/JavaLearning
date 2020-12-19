package com.ayy.exceptions;

import org.junit.Test;

/**
 * @ ClassName FinallyTest
 * @ Description test the order of a block of try-catch-finally
 * @ Author Zhao JIN
 * @ Date 30/10/2020 12:39
 * @ Version 1.0
 */
public class FinallyTest {
    @Test
    public void test01(){
        System.out.println(new WithReturn().methodB(1));
    }
}
