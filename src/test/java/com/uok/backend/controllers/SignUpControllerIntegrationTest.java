package com.uok.backend.controllers;

import com.uok.backend.BackendApplication;
import com.uok.backend.domains.User;
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
class SignUpControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    HttpHeaders headers = new HttpHeaders();

    @Test
    void signUp_newUserDetailsGiven_shouldReturnHttp200() throws Exception {

        User user = new User("testNew@test.com","firstName","lastName","student","test");

        HttpEntity<User> entity = new HttpEntity<User>(user, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/signup"),
                HttpMethod.POST, entity, String.class);

        assertEquals(200, response.getStatusCodeValue());

    }

    @Test
    void signUp_existingUserDetailsGiven_shouldReturnHttp400() throws Exception {

        User user = new User("test@test.com","firstName","lastName","student","test");

        HttpEntity<User> entity = new HttpEntity<User>(user, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/signup"),
                HttpMethod.POST, entity, String.class);

        assertEquals(400, response.getStatusCodeValue());

    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}