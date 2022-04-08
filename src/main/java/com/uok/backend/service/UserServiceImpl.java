package com.uok.backend.service;

import com.uok.backend.domain.User;
import com.uok.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean signup(User user) {
        String email = user.getEmail();

        if (userRepository.findByEmail(email) == null) {
            //userRepository.save(user);
            System.out.println("User registered");
            return true;
        } else {
            return false;
        }
    }

}
