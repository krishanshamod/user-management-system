package com.uok.backend.services;

import com.uok.backend.domains.User;
import com.uok.backend.domains.UserSignInDetails;
import com.uok.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UmsUserService implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashingService hashingService;


    @Override
    public boolean signUp(User user) {
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

    @Override
    public boolean signIn(UserSignInDetails userSignInDetails) {
        String email = userSignInDetails.getEmail();
        String password = userSignInDetails.getPassword();

        // check if the user exists or not
        if (userRepository.findByEmail(email) != null) {

            // get hashed password from the database
            String hashedPassword = userRepository.findByEmail(email).getPassword();

            // check if the password matches
            boolean isPasswordCorrect = hashingService.verify(password, hashedPassword);

            if (isPasswordCorrect) {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }

    }

}
