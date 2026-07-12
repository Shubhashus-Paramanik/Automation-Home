package com.main.ajarul.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.ajarul.service.MQTTService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/test")
public class TestController {
    //this is the protected endpoint
    // @GetMapping("/protected")
    // public String protectedApi() {
    //     return "JWT Authentication Works";
    // }
    
    @Autowired
    private MQTTService mqttService;

    @PostMapping
    public String test() {

        mqttService.publish(
                "home/test",
                "Hello MQTT");

        return "Published";
    }


}
