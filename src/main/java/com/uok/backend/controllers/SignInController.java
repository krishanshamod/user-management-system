package com.uok.backend.controllers;

import com.uok.backend.domains.UserSignInDetails;
import com.uok.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignInController {

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public String signIn(@RequestBody UserSignInDetails userSignInDetails) {
        return userService.signIn(userSignInDetails);
    }
}