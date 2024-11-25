package com.example.statisticsservice.controller;

import com.example.statisticsservice.request.UserReq;
import com.example.statisticsservice.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    // Add a new user
    @PostMapping("")
    public String addUser(@RequestBody UserReq userRequest) {
        userService.createUser(userRequest.getUserName(), userRequest.getEmail(), userRequest.getPassword());
        return "User added successfully: " + userRequest.getUserName();
    }

}
