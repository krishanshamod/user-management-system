package com.uok.backend.security.TokenGenerator;

public interface TokenGenerator {
    public String generateToken(String email, String firstName, String lastName, String role);
}
