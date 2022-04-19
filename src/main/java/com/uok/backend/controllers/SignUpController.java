package com.uok.backend.controllers;

import com.uok.backend.domains.User;
import com.uok.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {

    private UserService userService;

    @Autowired
    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody User user){
        return userService.signUp(user);
    }
}
