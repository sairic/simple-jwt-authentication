package com.sairic.example.simplejwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class SimpleJWTController {

    @GetMapping("/world")
    public LoggedInUser helloWord() {
        return new LoggedInUser("sairic", "ciawjeg");
    }
}
