package com.main.ajarul.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class Homcontroller {

    @GetMapping("/")
    public String home() {
        return "Home Automation backend Running";
    }
    
}
