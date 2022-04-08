package com.uok.backend.api;

import com.uok.backend.domain.User;
import com.uok.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignupController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public Boolean signup(@RequestBody User user){
        return userService.signup(user);
    }
}
