package com.uok.backend.services;

public interface HashingService {
    public String hash(String input);
    public boolean verify(String input, String hash);
}
