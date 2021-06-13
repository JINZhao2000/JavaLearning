package com.ayy;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 13/06/2021
 * @ Version 1.0
 */

public class OldServer {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(10000);

        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            try {
                long begin = System.currentTimeMillis();
                byte[] byteArray = new byte[4096];
                while (true) {
                    int read = dataInputStream.read(byteArray, 0, byteArray.length);
                    if (read == -1) {
                        break;
                    }

                }
                System.out.println(System.currentTimeMillis() - begin);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
