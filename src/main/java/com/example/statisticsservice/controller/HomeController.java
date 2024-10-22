package com.example.statisticsservice.controller;

import org.springframework.web.bind.annotation.*;


@RestController
public class HomeController {

    @GetMapping("/")
    public String getAllUsers() {
        return "You came to Main URL for statistic service";
    }

}
