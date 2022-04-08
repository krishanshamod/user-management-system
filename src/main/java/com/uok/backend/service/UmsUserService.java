package com.uok.backend.service;

import com.uok.backend.domain.User;
import com.uok.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UmsUserService implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashingService hashingService;


    @Override
    public boolean signup(User user) {
        String email = user.getEmail();
        String password = user.getPassword();

        // hash the password and set it to the user
        String hashedPassword = hashingService.hash(password);
        user.setPassword(hashedPassword);

        // check if the user already exists or not and add user to the database
        if (userRepository.findByEmail(email) == null) {
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

}
