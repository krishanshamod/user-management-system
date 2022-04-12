package com.uok.backend.services;

import com.uok.backend.domains.User;
import com.uok.backend.domains.JwtRequest;

public interface UserService {
    public boolean signUp(User user);
    public String signIn(JwtRequest jwtRequest);
}
