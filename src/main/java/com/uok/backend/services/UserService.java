package com.uok.backend.services;

import com.uok.backend.domains.User;
import com.uok.backend.domains.UserSignInDetails;

public interface UserService {
    public boolean signUp(User user);
    public boolean signIn(UserSignInDetails userSignInDetails);
}
