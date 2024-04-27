package com.skola.rest.controller;

import com.skola.rest.Entity.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.skola.rest.Entity.User;
import com.skola.rest.Database;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        UUID uuid = UUID.randomUUID();
        for (Map.Entry<UUID, User> entry : Database.userHashMap.entrySet()) {
            User userValue = entry.getValue();
            if (userValue != null && userValue.getEmail().equals(user.getEmail())) {
                return new ResponseEntity<>("USER ALREADY CREATED", HttpStatus.NOT_FOUND);
            }
        }
        user.setUuid(uuid);
        Database.userHashMap.put(uuid, user);
        System.out.println("User email: " + user.getEmail());
        return new ResponseEntity<>("USER EMAIL: " + user.getEmail(), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<String> getUser(@RequestParam(value = "email") String email) {
        for (Map.Entry<UUID, User> entry : Database.userHashMap.entrySet()) {
            User user = entry.getValue();
            if (user != null && user.getEmail().equals(email)) {
                return new ResponseEntity<>("User email: " + user.getEmail() + "  UserUUID: " + user.getUuid(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
}