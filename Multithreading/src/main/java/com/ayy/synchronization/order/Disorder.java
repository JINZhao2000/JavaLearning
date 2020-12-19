package com.ayy.synchronization.order;

/**
 * @ ClassName Disorder
 * @ Description
 * @ Author Zhao JIN
 * @ Date 10/11/2020 17
 * @ Version 1.0
 */
public class Disorder {
    private  static int x=0,y=0;
    private  static int a=0,b=0;

    public static void main (String[] args) throws InterruptedException{
        int j=0;
        for(;;) {
            j++;
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            Thread t1 = new Thread() {
                public void run () {
                    a = 1;
                    x = b;
                }
            };
            Thread t2 = new Thread() {
                public void run () {
                    b = 1;
                    y = a;
                }
            };
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            if (x == 0 && y == 0) {
                String resultat = "A la " + j + " eme fois, x = " + x + " y = " + y;
                System.out.println(resultat);
                break;
            } else {
                System.out.println("bon");
            }
        }
    }

    public static void shortWait(long interval){
        long start = System.nanoTime();
        long end;
        do{
            end=System.nanoTime();
        }while(start+interval>=end);
    }
}
