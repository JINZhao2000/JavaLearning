package com.ayy.service.impl;

import com.ayy.service.TicketService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 04/04/2021
 * @ Version 1.0
 */
@Service
@DubboService
public class TicketServiceImpl implements TicketService {
    @Override
    public String getTicket() {
        return "A ticket";
    }
}
