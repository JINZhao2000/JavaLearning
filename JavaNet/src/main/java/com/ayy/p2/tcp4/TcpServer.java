package com.ayy.p2.tcp4;

import java.io.*;
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
        InputStream is = new BufferedInputStream(socket.getInputStream());
        OutputStream os = new BufferedOutputStream(new FileOutputStream("..."));
        byte[] flush = new byte[1024];
        int len = -1;
        while((len=is.read(flush))!=-1){
            os.write(flush,0,len);
        }
        os.flush();
        os.close();
        socket.close();
        server.close();
    }
}
