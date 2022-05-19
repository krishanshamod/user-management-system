package com.uok.backend.services;

import com.uok.backend.domains.User;
import com.uok.backend.repositories.UserRepository;
import com.uok.backend.security.PasswordGenerator.HashedPasswordGenerator;
import com.uok.backend.security.TokenGenerator.TokenGenerator;
import com.uok.backend.utils.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

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

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldThrowWhenUserAlreadyExistsWhenSigningUpAUser() {
        //given
        String password = "originalPassword";
        User user = new User("pasandevin@gmail.com", "Pasan", "Jayawardene", "student", password);

        //when
        when(userRepository.findByEmail(any())).thenReturn(user);
        ResponseEntity response = underTest.signUp(user);

        //then
        ArgumentCaptor<String> emailArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userRepository).findByEmail(emailArgumentCaptor.capture());
        String capturedEmail = emailArgumentCaptor.getValue();
        assertThat(capturedEmail).isEqualTo(user.getEmail());

        ArgumentCaptor<String> errorMessageCaptor = ArgumentCaptor.forClass(String.class);
        verify(logger).logException(errorMessageCaptor.capture());
        String capturedErrorMessage = errorMessageCaptor.getValue();
        assertThat(capturedErrorMessage).isEqualTo("User already exists");

        verify(hashedPasswordGenerator, never()).hash(any());

        verify(userRepository, never()).save(any());

        assertThat(response.getStatusCodeValue()).isEqualTo(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void shouldThrowWhenUserEmailIsMissingWhenSigningUpAUser() {
        //given
        String password = "originalPassword";
        User user = new User(null, "Pasan", "Jayawardene", "student", password);

        //when
        ResponseEntity response = underTest.signUp(user);

        //then
        ArgumentCaptor<String> errorMessageCaptor = ArgumentCaptor.forClass(String.class);
        verify(logger).logException(errorMessageCaptor.capture());
        String capturedErrorMessage = errorMessageCaptor.getValue();
        assertThat(capturedErrorMessage).isEqualTo("Input Data missing");

        verify(userRepository, never()).findByEmail(any());

        verify(hashedPasswordGenerator, never()).hash(any());

        verify(userRepository, never()).save(any());

        assertThat(response.getStatusCodeValue()).isEqualTo(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void signIn() {
    }
}