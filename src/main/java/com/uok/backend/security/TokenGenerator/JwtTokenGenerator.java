package com.uok.backend.security.TokenGenerator;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenGenerator implements TokenGenerator {

    private String secret = "OurLittleSecretIsHere";

    @Override
    public String generate(String email, String firstName, String lastName, String role) {

        // Generate token
        String token = Jwts.builder()
                .claim("email", email)
                .claim("firstName", firstName)
                .claim("lastName", lastName)
                .claim("role", role)
                .signWith(SignatureAlgorithm.HS512, secret )
                .compact();

        return token;
    }
}