package com.uok.backend.controllers;

import com.uok.backend.domains.User;
import com.uok.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class SignUpController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public Boolean signUp(HttpServletResponse response, @RequestBody User user){
        return userService.signUp(response, user);
    }
}
