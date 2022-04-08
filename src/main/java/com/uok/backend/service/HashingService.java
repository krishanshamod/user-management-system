package com.uok.backend.service;

public interface HashingService {
    public String hash(String input);
    public boolean verify(String input, String hash);
}
