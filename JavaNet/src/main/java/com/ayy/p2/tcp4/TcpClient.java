package com.ayy.p2.tcp4;

import java.io.*;
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
        InputStream is = new BufferedInputStream(new FileInputStream("..."));
        OutputStream os = new BufferedOutputStream(socket.getOutputStream());
        byte[] flush = new byte[1024];
        int len = -1;
        while((len=is.read(flush))!=-1){
            os.write(flush,0,len);
        }
        os.flush();
        os.close();
        is.close();
        socket.close();
    }
}
