package com.uok.backend.security.PasswordGenerator;

import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
public class SHA256HashedPasswordGenerator implements HashedPasswordGenerator {
    @Override
    public String hash(String input) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean verify(String input, String hash) {
        return hash.equals(hash(input));
    }
}
