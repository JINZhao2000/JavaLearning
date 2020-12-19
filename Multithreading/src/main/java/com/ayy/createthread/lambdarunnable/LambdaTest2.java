package com.ayy.createthread.lambdarunnable;

/**
 * @ ClassName LambdaTest1
 * @ Description affectation of lambda
 * @ Author Zhao JIN
 * @ Date 09/11/2020 16:00
 * @ Version 1.0
 */
public class LambdaTest2 {
    public static void main (String[] args) {
        ILove love = a-> System.out.println(a);
        love.lambda(1);
    }
}
