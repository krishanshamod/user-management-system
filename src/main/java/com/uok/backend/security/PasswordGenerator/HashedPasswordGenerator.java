package com.uok.backend.security.PasswordGenerator;

public interface HashedPasswordGenerator {
    public String hash(String input);
    public boolean verify(String input, String hash);
}
