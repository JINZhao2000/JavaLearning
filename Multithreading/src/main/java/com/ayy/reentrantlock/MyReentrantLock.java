package com.ayy.reentrantlock;

/**
 * @ ClassName MyReentrantLock
 * @ Description my reentrant lock
 * @ Author Zhao JIN
 * @ Date 12/11/2020 10
 * @ Version 1.0
 */
public class MyReentrantLock {
    ReLock reLock = new ReLock();

    public void a() throws InterruptedException {
        reLock.lock();
        System.out.println("--- a locked---");
        doSomething();
        reLock.unlock();
        System.out.println("--- a unlocked ---");
        ;
    }

    public void doSomething() throws InterruptedException {
        reLock.lock();
        System.out.println("--- do something ---");
        reLock.unlock();
    }

    public static void main(String[] args) throws InterruptedException {
        MyReentrantLock myReentrantLock = new MyReentrantLock();
        myReentrantLock.a();
        myReentrantLock.doSomething();
    }

}

class ReLock {
    private boolean isLocked = false;
    private Thread lockedBy = null;
    private int lockCount = 0;

    public synchronized void lock() throws InterruptedException {
        Thread t = Thread.currentThread();
        while (isLocked && lockedBy != t) {
            this.wait();
        }
        isLocked = true;
        lockedBy = t;
        lockCount++;
    }

    public synchronized void unlock() {
        if (Thread.currentThread() == lockedBy) {
            lockCount--;
            if (lockCount == 0) {
                isLocked = false;
                this.notify();
                lockedBy = null;
            }
        }
    }
}