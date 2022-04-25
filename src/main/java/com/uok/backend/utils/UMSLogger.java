package com.uok.backend.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class UMSLogger implements Logger {

    DateTimeFormatter dateTimeFormatter;
    LocalDateTime localDateTime;

    public UMSLogger() {
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public void logApiCall(String endpointName) {
        localDateTime = LocalDateTime.now();

        System.out.println(
                dateTimeFormatter.format(localDateTime) + " " +  "ApiCall:"+ " " + endpointName + " " + "called"
        );
    }

    @Override
    public void logSuccess(String endpointName) {
        localDateTime = LocalDateTime.now();

        System.out.println(
                dateTimeFormatter.format(localDateTime) + " " +  "ApiResponse:"+ " " + endpointName + " " + "success"
        );
    }

    @Override
    public void logException(String exceptionMessage) {
        localDateTime = LocalDateTime.now();

        System.out.println(
                dateTimeFormatter.format(localDateTime) + " " +  "Exception:"+ " " + exceptionMessage
        );
    }
}
