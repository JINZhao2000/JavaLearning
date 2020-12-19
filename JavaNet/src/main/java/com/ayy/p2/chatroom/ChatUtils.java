package com.ayy.p2.chatroom;

/**
 * @ ClassName ChatUtils
 * @ Description
 * @ Author Zhao JIN
 * @ Date 15/12/2020 10H
 * @ Version 1.0
 */
public class ChatUtils {
    public static void release(AutoCloseable... acs){
        for (AutoCloseable ac : acs){
            try {
                if(ac!=null) {
                    ac.close();
                }
            } catch (Exception e) {
                System.out.println("Error in release");
            }
        }
    }
}
