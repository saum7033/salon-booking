package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

public class HomeController {
    @GetMapping
    public String HomeControllerHandler(){
        return "salon microservices";
    }

}

