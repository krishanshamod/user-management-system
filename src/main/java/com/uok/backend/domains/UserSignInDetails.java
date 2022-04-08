package com.uok.backend.domains;

public class UserSignInDetails {
    private String email;
    private String password;

    public UserSignInDetails() {
    }

    public UserSignInDetails(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
