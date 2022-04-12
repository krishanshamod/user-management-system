package com.uok.backend.controllers;

import com.uok.backend.domains.SignInRequest;
import com.uok.backend.domains.SignInResponse;
import com.uok.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class SignInController {

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public SignInResponse signIn(HttpServletResponse response, @RequestBody SignInRequest signInRequest) {
        return userService.signIn(response, signInRequest);
    }
}
