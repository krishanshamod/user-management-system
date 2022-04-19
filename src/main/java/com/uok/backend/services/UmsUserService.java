package com.uok.backend.services;

import com.uok.backend.domains.SignInResponse;
import com.uok.backend.domains.User;
import com.uok.backend.domains.SignInRequest;
import com.uok.backend.exceptions.DataMissingException;
import com.uok.backend.exceptions.FailedLoginException;
import com.uok.backend.exceptions.FailedRegistrationException;
import com.uok.backend.repositories.UserRepository;
import com.uok.backend.security.PasswordGenerator.HashedPasswordGenerator;
import com.uok.backend.security.TokenGenerator.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
public class UmsUserService implements UserService {

    private UserRepository userRepository;

    private HashedPasswordGenerator hashedPasswordGenerator;

    private TokenGenerator tokenGenerator;

    @Autowired
    public UmsUserService(UserRepository userRepository, HashedPasswordGenerator hashedPasswordGenerator
            , TokenGenerator tokenGenerator) {
        this.userRepository = userRepository;
        this.hashedPasswordGenerator = hashedPasswordGenerator;
        this.tokenGenerator = tokenGenerator;
    }


    @Override
    public ResponseEntity signUp(User user) {
        String email = user.getEmail();
        String password = user.getPassword();

        try {
            // check all data is received or not
            if (user.getEmail() == null || user.getPassword() == null
                    || user.getFirstName() == null || user.getLastName() == null) {
                throw new DataMissingException("Input Data missing");
            }

            // hash the password and set it to the user
            String hashedPassword = hashedPasswordGenerator.hash(password);
            user.setPassword(hashedPassword);

            // check if the user already exists or not and add user to the database
            if (userRepository.findByEmail(email) == null) {
                userRepository.save(user);
                return ResponseEntity.ok(null);
            } else {
                throw new FailedRegistrationException("User already exists");
            }

        } catch (FailedRegistrationException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(null);
        } catch (DataMissingException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(null);
        }
    }

    @Override
    public ResponseEntity signIn(SignInRequest signInRequest) {
        String email = signInRequest.getEmail();
        String password = signInRequest.getPassword();

        try {
            // check all data is received or not
            if (email == null || password == null) {
                throw new DataMissingException("Email or password is missing");
            }

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

                    return ResponseEntity.ok(new SignInResponse(token));

                } else {
                    throw new FailedLoginException("Invalid password");
                }
            } else {
                throw new FailedLoginException("User does not exist");
            }

        } catch (FailedLoginException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(null);
        } catch (DataMissingException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(null);
        }
    }
}
