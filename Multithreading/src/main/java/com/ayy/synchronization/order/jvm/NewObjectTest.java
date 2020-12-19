package com.ayy.synchronization.order.jvm;


import org.openjdk.jol.info.ClassLayout;

/**
 * @ ClassName NewObjectTest
 * @ Description use of jol to get header of object
 * @ Author Zhao JIN
 * @ Date 10/11/2020 17
 * @ Version 1.0
 */
public class NewObjectTest {
    public static void main (String[] args) throws  Exception{
//        Thread.sleep(4047);
        Object o = new Object();
//        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        synchronized (o){
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }
}
