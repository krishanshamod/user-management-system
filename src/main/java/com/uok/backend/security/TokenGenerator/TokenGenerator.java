package com.uok.backend.security.TokenGenerator;

public interface TokenGenerator {
    public String generate(String email, String firstName, String lastName, String role);
}
