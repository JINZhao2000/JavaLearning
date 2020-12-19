package com.ayy.p1;

import java.net.InetSocketAddress;

/**
 * @ ClassName PortDemo
 * @ Description
 * @ Author Zhao JIN
 * @ Date 11/12/2020 22H
 * @ Version 1.0
 */
public class PortDemo {
    public static void main(String[] args) {
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1",8080);
        System.out.println(socketAddress.getHostName()+"--"+socketAddress.getAddress());
        System.out.println(socketAddress.getPort());
    }
}
