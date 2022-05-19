package com.uok.backend.services;

import com.uok.backend.domains.User;
import com.uok.backend.repositories.UserRepository;
import com.uok.backend.security.PasswordGenerator.HashedPasswordGenerator;
import com.uok.backend.security.TokenGenerator.TokenGenerator;
import com.uok.backend.utils.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UmsUserService.class})
@ExtendWith(MockitoExtension.class)
class UmsUserServiceTest {

    @Autowired
    private UmsUserService underTest;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HashedPasswordGenerator hashedPasswordGenerator;

    @Mock
    private TokenGenerator tokenGenerator;

    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
        underTest = new UmsUserService(userRepository, hashedPasswordGenerator, tokenGenerator, logger);
    }

    @Test
    void shouldSignUpAUser() {
        //given
        String password = "originalPassword";
        User user = new User("pasandevin@gmail.com", "Pasan", "Jayawardene", "student", password);

        String hashedPassword = "HashedPassword";

        //when
        when(userRepository.findByEmail(any())).thenReturn(null);
        when(hashedPasswordGenerator.hash(any())).thenReturn(hashedPassword);
        ResponseEntity response = underTest.signUp(user);

        //then
        ArgumentCaptor<String> emailArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userRepository).findByEmail(emailArgumentCaptor.capture());
        String capturedEmail = emailArgumentCaptor.getValue();
        assertThat(capturedEmail).isEqualTo(user.getEmail());

        ArgumentCaptor<String> passwordArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(hashedPasswordGenerator).hash(passwordArgumentCaptor.capture());
        String capturedPassword = passwordArgumentCaptor.getValue();
        assertThat(capturedPassword).isEqualTo(password);

        assertThat(user.getPassword()).isEqualTo(hashedPassword);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void signIn() {
    }
}