package com.ayy.p2.udp4;

/**
 * @ ClassName Client2
 * @ Description
 * @ Author Zhao JIN
 * @ Date 14/12/2020 12H
 * @ Version 1.0
 */
public class Client2 {
    public static void main(String[] args) {
        new Thread(new SendMsg(10000,"127.0.0.1",10001)).start();
        new Thread(new ReceiveMsg(10003)).start();
    }
}
