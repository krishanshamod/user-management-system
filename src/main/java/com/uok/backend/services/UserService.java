package com.uok.backend.services;

import com.uok.backend.domains.SignInResponse;
import com.uok.backend.domains.SignUpResponse;
import com.uok.backend.domains.User;
import com.uok.backend.domains.SignInRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public interface UserService {
    public SignUpResponse signUp(HttpServletResponse response, User user);
    public ResponseEntity signIn(SignInRequest signInRequest);
}
