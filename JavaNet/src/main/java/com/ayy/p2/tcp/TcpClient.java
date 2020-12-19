package com.ayy.p2.tcp;

import java.io.DataOutputStream;
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
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        String msg = "hello";
        dos.writeUTF(msg);
        dos.flush();
        dos.close();
        socket.close();
    }
}
