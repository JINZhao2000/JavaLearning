package com.ayy.reentrantlock;


/**
 * @ ClassName MyNonReentrantLock
 * @ Description my non-reentrant lock
 * @ Author Zhao JIN
 * @ Date 12/11/2020 10
 * @ Version 1.0
 */
public class MyNonReentrantLock {
    Lock lock = new Lock();
    public void a() throws InterruptedException {
        lock.lock();
        System.out.println("--- a locked---");
        doSomething();
        lock.unlock();
        System.out.println("--- a unlocked ---");;
    }

    public void doSomething() throws InterruptedException {
        lock.lock();
        System.out.println("--- do something ---");
        lock.unlock();
    }

    public static void main(String[] args) throws InterruptedException {
        MyNonReentrantLock myNonReentrantLock = new MyNonReentrantLock();
        myNonReentrantLock.a();
        myNonReentrantLock.doSomething();
    }
}

class Lock {
    private boolean isLocked = false;

    public synchronized void lock() throws InterruptedException {
        while (isLocked) {
            this.wait();
        }
        isLocked = true;
    }

    public synchronized void unlock() {
        isLocked = false;
        this.notify();
    }
}