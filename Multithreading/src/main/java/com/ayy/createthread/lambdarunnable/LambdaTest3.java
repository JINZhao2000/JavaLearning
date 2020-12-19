package com.ayy.createthread.lambdarunnable;

/**
 * @ ClassName LambdaTest1
 * @ Description affectation of lambda by parameters
 * @ Author Zhao JIN
 * @ Date 09/11/2020 16:00
 * @ Version 1.0
 */
public class LambdaTest3 {
    public static void main (String[] args) {
        IHate hate =(c,d) -> c+d;//return
        System.out.println(hate.lambda(100, 200));
    }
}
