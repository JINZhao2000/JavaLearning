package com.ayy.jvm.loader;

/**
 * @ ClassName Demo1
 * @ Description
 * @ Author Zhao JIN
 * @ Date 02/01/2021 20H
 * @ Version 1.0
 */
public class Demo1 {
    public static void main(String[] args) {
        // new A();
        /*
        * Initialized A
        * Constructor A
        */
        // new B();
        /*
        * Initialized A
        * Initialized B
        * Constructor A
        * Constructor B
        */
        // new A();
        // new B();
        /*
         * Initialized A
         * Constructor A
         * Initialized B
         * Constructor A
         * Constructor B
         */
        new B();
        new A();
        /*
         * Initialized A
         * Initialized B
         * Constructor A
         * Constructor B
         * Constructor A
         */
    }
}

class A{
    static int i;

    static {
        System.out.println("Initialized A");
    }

    public A(){
        System.out.println("Constructor A");
    }
}

class B extends A{
    static int j;

    static {
        System.out.println("Initialized B");
    }

    public B(){
        System.out.println("Constructor B");
    }
}