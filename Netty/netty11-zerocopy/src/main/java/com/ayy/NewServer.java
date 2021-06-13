package com.ayy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 13/06/2021
 * @ Version 1.0
 */

public class NewServer {
    public static void main(String[] args) throws Exception{
        InetSocketAddress address = new InetSocketAddress(10000);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(address);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);

            int read = 0;
            long begin = System.currentTimeMillis();
            while (-1 != read) {
                try {
                    read = socketChannel.read(byteBuffer);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                byteBuffer.rewind();
            }
            System.out.println(System.currentTimeMillis() - begin);
        }
    }
}
