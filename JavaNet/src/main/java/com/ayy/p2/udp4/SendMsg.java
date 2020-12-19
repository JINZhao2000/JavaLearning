package com.ayy.p2.udp4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * @ ClassName SendMsg
 * @ Description
 * @ Author Zhao JIN
 * @ Date 14/12/2020 12H
 * @ Version 1.0
 */
public class SendMsg implements Runnable {
    private DatagramSocket socket;
    private BufferedReader reader;
    private String destIP;
    private int destPort;
    public SendMsg(int srcPort,String destIP, int destPort) {
        try {
            this.socket = new DatagramSocket(srcPort);
            this.destIP = destIP;
            this.destPort = destPort;
            reader = new BufferedReader(new InputStreamReader(System.in));
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = reader.readLine();
                byte[] data = msg.getBytes();
                DatagramPacket packet = new DatagramPacket(data, 0, data.length, new InetSocketAddress(destIP,destPort));
                socket.send(packet);
                if (msg.equals("quit")) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            socket.close();
        }
    }
}
