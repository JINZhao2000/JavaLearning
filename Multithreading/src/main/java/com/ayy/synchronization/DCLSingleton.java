package com.ayy.synchronization;

/**
 * @ ClassName DCLSingleton
 * @ Description Double Check Lock
 * @ Author Zhao JIN
 * @ Date 10/11/2020 17
 * @ Version 1.0
 */
public class DCLSingleton {
    private static volatile DCLSingleton INSTANCE;

    public static DCLSingleton getInstance(){
        if(INSTANCE == null){
            synchronized (DCLSingleton.class){
                if(INSTANCE==null){
                    try{
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    INSTANCE = new DCLSingleton();
                }
            }
        }
        return INSTANCE;
    }
}
