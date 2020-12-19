package com.ayy.p2.tcp2;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ ClassName TcpServer
 * @ Description
 * @ Author Zhao JIN
 * @ Date 14/12/2020 20H
 * @ Version 1.0
 */
public class TcpServer {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(10000);
        Socket socket = server.accept();
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        String msg = dis.readUTF();
        String[] data = msg.split("&");
        String name = data[0];
        String pwd = data[1];
        if(name.equals("abc")&&pwd.equals("1111")){
            System.out.println("Login success");
        }else{
            System.out.println("Login failed");
        }
        dis.close();
        socket.close();
        server.close();
    }
}
