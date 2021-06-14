package com.ayy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 14/06/2021
 * @ Version 1.0
 */

public class NewClient {
    private static final String file = "D:/Downloads/ehsetup.zip";

    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 10000));
        socketChannel.configureBlocking(true);

        FileChannel fileChannel = new FileInputStream(file).getChannel();

        long begin = System.currentTimeMillis();

        fileChannel.transferTo(0, fileChannel.size(), socketChannel);

        System.out.println(System.currentTimeMillis() - begin);
    }
}
