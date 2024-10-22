package com.example.statisticsservice.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

//    private List<String> users = new ArrayList<>();
//
//    // Get all users
//    @GetMapping("/users")
//    public List<String> getAllUsers() {
//        return users;
//    }
//
//    // Add a new user
//    @PostMapping("/users")
//    public String addUser(@RequestBody String user) {
//        users.add(user);
//        return "User added successfully: " + user;
//    }
//
//    // Get a specific user by index
//    @GetMapping("/users/{index}")
//    public String getUser(@PathVariable int index) {
//        if (index >= 0 && index < users.size()) {
//            return users.get(index);
//        } else {
//            return "User not found";
//        }
//    }
//
//    // Delete a user by index
//    @DeleteMapping("/users/{index}")
//    public String deleteUser(@PathVariable int index) {
//        if (index >= 0 && index < users.size()) {
//            String removedUser = users.remove(index);
//            return "User removed successfully: " + removedUser;
//        } else {
//            return "User not found";
//        }
//    }
}
