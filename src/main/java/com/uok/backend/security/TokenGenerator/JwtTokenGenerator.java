package com.uok.backend.security.TokenGenerator;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenGenerator implements TokenGenerator {

    private Environment env;
    private String secret;

    @Autowired
    public JwtTokenGenerator(Environment env) {
        this.env = env;
        this.secret = env.getProperty("token.secret");
    }

    @Override
    public String generate(String email, String firstName, String lastName, String role) {

        // Generate and return token
        return Jwts.builder()
                .claim("email", email)
                .claim("firstName", firstName)
                .claim("lastName", lastName)
                .claim("role", role)
                .signWith(SignatureAlgorithm.HS512, secret )
                .compact();
    }
}
