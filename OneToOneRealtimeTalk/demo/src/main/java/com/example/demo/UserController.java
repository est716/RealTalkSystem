package com.example.demo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RestMapping("/api/user") // RestMapping 走http連線，與websocket區隔
public class UserController {
    
    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody String userName) {
        User user = new User(java.util.UUID.randomUUID().toString(), userName);
        return ResponseEntity.ok(user);
    }
    
}
