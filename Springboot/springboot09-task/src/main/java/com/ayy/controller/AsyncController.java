package com.ayy.controller;

import com.ayy.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 04/04/2021
 * @ Version 1.0
 */
@RestController
public class AsyncController {
    private AsyncService asyncService;

    @Autowired
    public void setAsyncService(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

    @RequestMapping("/hello")
    public String hello(){
        asyncService.hello();
        return "OK";
    }
}
