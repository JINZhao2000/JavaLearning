package com.ayy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 13/06/2021
 * @ Version 1.0
 */

public class OldClient {
    private static final String file = "D:/Downloads/ehsetup.zip";
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("localhost", 10000);
        InputStream inputStream = new FileInputStream(file);
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

        byte[] buffer = new byte[4096];
        long readCount;
        long total = 0;

        long begin = System.currentTimeMillis();

        while ((readCount = inputStream.read(buffer))>=0){
            total+=readCount;
            outputStream.write(buffer);
        }

        System.out.println("Total : " + total + ", Time : "+ (System.currentTimeMillis() - begin));

        outputStream.close();
        socket.close();
        inputStream.close();
    }
}
