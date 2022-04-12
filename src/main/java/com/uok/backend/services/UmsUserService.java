package com.uok.backend.services;

import com.uok.backend.domains.SignInResponse;
import com.uok.backend.domains.User;
import com.uok.backend.domains.SignInRequest;
import com.uok.backend.repositories.UserRepository;
import com.uok.backend.security.PasswordGenerator.HashedPasswordGenerator;
import com.uok.backend.security.TokenGenerator.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
public class UmsUserService implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashedPasswordGenerator hashedPasswordGenerator;

    @Autowired
    private TokenGenerator tokenGenerator;


    @Override
    public boolean signUp(HttpServletResponse response, User user) {
        String email = user.getEmail();
        String password = user.getPassword();

        // hash the password and set it to the user
        String hashedPassword = hashedPasswordGenerator.hash(password);
        user.setPassword(hashedPassword);

        // check if the user already exists or not and add user to the database
        if (userRepository.findByEmail(email) == null) {
            userRepository.save(user);
            response.setStatus(200);
            return true;
        } else {
            response.setStatus(400);
            response.setHeader("Error", "User already exists");
            return false;
        }
    }

    @Override
    public SignInResponse signIn(HttpServletResponse response, SignInRequest signInRequest) {
        String email = signInRequest.getEmail();
        String password = signInRequest.getPassword();

        // check if the user exists or not
        if (userRepository.findByEmail(email) != null) {

            // get hashed password from the database
            String hashedPassword = userRepository.findByEmail(email).getPassword();

            // check if the password matches
            boolean isPasswordCorrect = hashedPasswordGenerator.verify(password, hashedPassword);

            if (isPasswordCorrect) {

                // generate token
                String token = tokenGenerator.generate(
                        userRepository.findByEmail(email).getEmail(),
                        userRepository.findByEmail(email).getFirstName(),
                        userRepository.findByEmail(email).getLastName(),
                        userRepository.findByEmail(email).getRole()
                );
                return new SignInResponse(token);

            } else {
                response.setStatus(401);
                response.setHeader("Error", "Invalid password");
                return null;
            }
        } else {
            response.setStatus(401);
            response.setHeader("Error", "User does not exist");
            return null;
        }
    }

}
