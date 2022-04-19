package com.uok.backend.services;

import com.uok.backend.domains.User;
import com.uok.backend.domains.SignInRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public ResponseEntity signUp(User user);
    public ResponseEntity signIn(SignInRequest signInRequest);
}
