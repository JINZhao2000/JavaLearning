package com.ayy.synchronization.order.signal;

/**
 * @ ClassName Manager2
 * @ Description manager the order of thread by signal
 * @ Author Zhao JIN
 * @ Date 10/11/2020 17
 * @ Version 1.0
 */
public class Manager2 {
    public static void main(String[] args) {
        Redlight redlight = new Redlight();
        new Cross(redlight).start();
        new Operator(redlight).start();
    }
}

class Cross extends Thread{
    private Redlight redlight;

    public Cross(Redlight redlight) {
        this.redlight = redlight;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            if(i%2==0){
                redlight.trun(1);
            }else{
                redlight.trun(0);
            }
        }
    }
}

class Operator extends Thread{
    private Redlight redlight;

    public Operator(Redlight redlight) {
        this.redlight = redlight;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            redlight.observe();
        }
    }
}

class Redlight{
    private int status;
    private boolean flag = true;

    public synchronized void trun(int status){
        if(!flag){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.status = status;
        System.out.println("change the signal to "+status);
        this.notifyAll();
        this.flag=!this.flag;
    }

    public synchronized void observe(){
        if(flag){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Redlignt is "+(status==1?"on":"off"));
        this.notifyAll();
        this.flag=!this.flag;
    }
}
