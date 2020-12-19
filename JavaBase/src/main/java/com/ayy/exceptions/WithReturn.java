package com.ayy.exceptions;

/**
 * @ ClassName WithReturn
 * @ Description the relation between the return and finally
 * @ Author Zhao JIN
 * @ Date 30/10/2020 12:39
 * @ Version 1.0
 */
public class WithReturn {
    public int methodA(int positive) throws IllegalArgumentException{
        if(--positive<=0){
            throw new IllegalArgumentException("Exception");
        }
        return positive;
    }

    public int methodB(int positive){
        try {
            System.out.println("Begin");
            int result = methodA(positive);
            return result;
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            // the statement of finally will run earlier than this return;
            return -100;
        }finally {
            // never put the return statement in finally, it will override the return in catch block
            System.out.println("Finally");
        }
    }
}
