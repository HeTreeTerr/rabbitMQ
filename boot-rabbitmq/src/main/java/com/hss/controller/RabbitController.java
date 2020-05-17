package com.hss.controller;

import com.hss.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitController {

    @Autowired
    private RabbitService rabbitService;

    @RequestMapping(value = "/sendPtoPMessage")
    public String sendPtoPMessage(@RequestParam("message") String message){
        rabbitService.sendPtoPMessage(message);
        return "success P to P";
    }

    @RequestMapping(value = "/sendFanoutMessage")
    public String sendFanoutMessage(@RequestParam("message") String message){
        rabbitService.sendFanoutMessage(message);
        return "success Exchange Fanout";
    }
}
