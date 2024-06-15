package com.skola.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @PostMapping()
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
        System.out.println(Database.userHashMap);
        System.out.println("User email: " + user.getEmail());
        return new ResponseEntity<>("USER EMAIL: " + user.getEmail(), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<String> getUser(@RequestParam(value = "email") String email) {
        for (Map.Entry<UUID, User> entry : Database.userHashMap.entrySet()) {
            User user = entry.getValue();
            if (user != null && user.getEmail().equals(email)) {
                return new ResponseEntity<>("User email: " + user.getEmail() + "  UserUUID: " + user.getUuid(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @PutMapping()
    public ResponseEntity<String> updateUser(@RequestParam(value = "oldEmail") String oldEmail, @RequestBody User user) {

        for (Map.Entry<UUID, User> entry : Database.userHashMap.entrySet()) {
            User userValue = entry.getValue();

            if (userValue.getEmail().equals(oldEmail)) {
                user.setUuid(userValue.getUuid());
                Database.userHashMap.put(userValue.getUuid(), user);
                System.out.println(user.getEmail());
                System.out.println(user.getPassword());
                return new ResponseEntity<>("User successfully Updated", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }


    @DeleteMapping()
    public ResponseEntity<String> deleteUser(@RequestParam(value = "email") String email) {
        for (Map.Entry<UUID, User> entry : Database.userHashMap.entrySet()) {
            User user = entry.getValue();
            if (user != null && user.getEmail().equals(email)) {
                Database.userHashMap.remove(user.getUuid());
                System.out.println(Database.userHashMap);
                return new ResponseEntity<>("User deleted", HttpStatus.OK);
            }

        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
}
