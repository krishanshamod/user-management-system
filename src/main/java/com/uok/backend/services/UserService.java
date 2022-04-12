package com.uok.backend.services;

import com.uok.backend.domains.SignInResponse;
import com.uok.backend.domains.User;
import com.uok.backend.domains.SignInRequest;

import javax.servlet.http.HttpServletResponse;

public interface UserService {
    public boolean signUp(HttpServletResponse response, User user);
    public SignInResponse signIn(HttpServletResponse response, SignInRequest signInRequest);
}
