package com.ayy.controller;

import com.ayy.bean.Dispatcher;
import com.ayy.service.DispatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 11/05/2021
 * @ Version 1.0
 */
@RestController
public class DispatcherController {
    @Autowired
    private DispatcherService dispatcherService;

    @GetMapping("/order/{oid}")
    public String order(@PathVariable("oid") String oid) throws InterruptedException {
        Thread.sleep(3000);
        dispatcherService.sendDispacher(new Dispatcher("did", oid, "1", "ocont", new Date(),"uid"));
        return "success";
    }
}
