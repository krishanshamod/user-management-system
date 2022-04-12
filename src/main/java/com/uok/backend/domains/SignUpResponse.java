package com.uok.backend.domains;

public class SignUpResponse {

    private boolean isRegistered;

    public SignUpResponse(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        this.isRegistered = registered;
    }
}
