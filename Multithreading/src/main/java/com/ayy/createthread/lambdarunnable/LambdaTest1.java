package com.ayy.createthread.lambdarunnable;

/**
 * @ ClassName LambdaTest1
 * @ Description lambda with multilines
 * @ Author Zhao JIN
 * @ Date 09/11/2020 16:00
 * @ Version 1.0
 */
public class LambdaTest1 {
    public static void main (String[] args) {
        ILike like = () -> {
            System.out.println("1");
            System.out.println("1");
            System.out.println("1");
        };
        like.Lambda();
    }
}
