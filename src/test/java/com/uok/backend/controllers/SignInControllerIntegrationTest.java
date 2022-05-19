package com.uok.backend.controllers;

import com.uok.backend.BackendApplication;
import com.uok.backend.domains.SignInRequest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SignInControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void signIn_correctDetailsGiven_shouldReturnHttp200() throws Exception {

        SignInRequest signInRequest = new SignInRequest("test@test.com","test");

        HttpEntity<SignInRequest> entity = new HttpEntity<SignInRequest>(signInRequest, headers);

        ResponseEntity response = restTemplate.exchange(
                createURLWithPort("/signin"),
                HttpMethod.POST, entity, String.class);

        assertEquals(200, response.getStatusCodeValue());

    }

    @Test
    public void signIn_wrongDetailsGiven_shouldReturnHttp401() throws Exception {

        SignInRequest signInRequest = new SignInRequest("test@test.com","wrongPassword");

        HttpEntity<SignInRequest> entity = new HttpEntity<SignInRequest>(signInRequest, headers);

        ResponseEntity response = restTemplate.exchange(
                createURLWithPort("/signin"),
                HttpMethod.POST, entity, String.class);

        assertEquals(401, response.getStatusCodeValue());

    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}