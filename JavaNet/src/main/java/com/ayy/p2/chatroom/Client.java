package com.ayy.p2.chatroom;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @ ClassName Client
 * @ Description
 * @ Author Zhao JIN
 * @ Date 15/12/2020 09H
 * @ Version 1.0
 */
public class Client {
    public static void main(String[] args) throws Exception {
        System.out.println("--- Client Start ---");
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Your name");
        String name = console.readLine();
        Socket socket = new Socket("localhost",10000);
        new Thread(new ClientSend(socket,name)).start();
        new Thread(new ClientReceive(socket)).start();
    }
}
