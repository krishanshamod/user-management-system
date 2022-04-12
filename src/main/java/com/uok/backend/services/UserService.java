package com.uok.backend.services;

import com.uok.backend.domains.JwtResponse;
import com.uok.backend.domains.User;
import com.uok.backend.domains.JwtRequest;

import javax.servlet.http.HttpServletResponse;

public interface UserService {
    public boolean signUp(HttpServletResponse response, User user);
    public JwtResponse signIn(HttpServletResponse response, JwtRequest jwtRequest);
}
