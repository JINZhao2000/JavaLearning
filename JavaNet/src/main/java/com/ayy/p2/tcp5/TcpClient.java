package com.ayy.p2.tcp5;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @ ClassName TcpClient
 * @ Description
 * @ Author Zhao JIN
 * @ Date 14/12/2020 20H
 * @ Version 1.0
 */
public class TcpClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost",10000);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Your name");
        String name = reader.readLine();
        System.out.println("Your pwd");
        String pwd = reader.readLine();

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF(name+"&"+pwd);
        dos.flush();
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        String status = dis.readUTF();
        System.out.println(status);

        dis.close();
        dos.close();
        socket.close();
    }
}
