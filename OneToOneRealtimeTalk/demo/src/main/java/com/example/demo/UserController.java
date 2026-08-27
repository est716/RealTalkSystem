package com.example.demo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class UserController {
    
    @PostMapping("/createUser")
    public User getMethodName(@RequestBody String userName) {
        User user = new User(java.util.UUID.randomUUID().toString(), userName);
        return user;
    }
    
}
