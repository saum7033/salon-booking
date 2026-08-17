package com.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {
    @GetMapping
    public String HomeControllerHandler(){
        return "salon microservices";
    }

}



