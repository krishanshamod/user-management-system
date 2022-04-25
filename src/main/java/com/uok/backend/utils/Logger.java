package com.uok.backend.utils;

public interface Logger {
    public void logApiCall(String endpointName);
    public void logSuccess(String endpointName);
    public void logException(String exceptionMessage);
}
