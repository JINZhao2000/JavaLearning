package com.ayy.service;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 04/04/2021
 * @ Version 1.0
 */
@Service
public class UserService {
    @DubboReference
    TicketService TICKET_SERVICE;

    public void aTicket(){
        String ticket = TICKET_SERVICE.getTicket();
        System.out.println(ticket);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
