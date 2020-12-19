package com.ayy.synchronization.order.a1b2c3;

/**
 * @ ClassName A1B2C3
 * @ Description print number and letter one by one
 * @ Author Zhao JIN
 * @ Date 09/11/2020 16:12
 * @ Version 1.0
 */
public class A1B2C3 {
    public static void main (String[] args) throws InterruptedException {
        A1B2C3 order = new A1B2C3();
        Thread thread1 = new Thread(()->order.letter());
        Thread thread2 = new Thread(()->order.number());
        thread1.start();
        thread2.start();
    }

    public synchronized void letter (){
        for (int i = 0; i < 26; i++) {
            System.out.println((char)(i+65));
            this.notify();
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.notify();
    }
    public synchronized void number (){
        for (int i = 0; i < 26; i++) {
            System.out.println(i+1);
            this.notify();
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.notify();
    }
}
