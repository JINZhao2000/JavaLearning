package com.ayy.createthread.lambdarunnable;

/**
 * @ ClassName CreationRunnable
 * @ Description infer a lambda
 * @ Author Zhao JIN
 * @ Date 09/11/2020 15:56
 * @ Version 1.0
 */
public class CreationLambda {
    public static void main (String[] args) {
        // how to infer a lambda

//		new Thread(new Runnable() {
//			@Override
//			public void run () {
//				System.out.println(1);
//			}
//		}).start();

        new Thread(()->{
            System.out.println(1);
        }).start();
    }
}
