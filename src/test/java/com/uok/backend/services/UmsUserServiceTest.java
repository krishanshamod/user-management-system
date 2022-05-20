package com.uok.backend.services;

import com.uok.backend.domains.SignInRequest;
import com.uok.backend.domains.SignInResponse;
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

import java.util.List;

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
        User user = new User(
                "pasandevin@gmail.com",
                "Pasan",
                "Jayawardene",
                "student",
                password
        );

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
        User user = new User(
                "pasandevin@gmail.com",
                "Pasan",
                "Jayawardene",
                "student",
                password
        );

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
    void shouldThrowWhenFirstNameIsMissingWhenSigningUpAUser() {
        //given
        String password = "originalPassword";
        User user = new User(
                "pasandevin@gmail.com",
                null,
                "Jayawardene",
                "student",
                password
        );

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
    void shouldThrowWhenLastNameIsMissingWhenSigningUpAUser() {
        //given
        String password = "originalPassword";
        User user = new User("pasandevin@gmail.com", "Pasan", null, "student", password);

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
    void shouldThrowWhenPasswordIsMissingWhenSigningUpAUser() {
        //given
        User user = new User(
                "pasandevin@gmail.com",
                "Pasan",
                "Jayawardene",
                "student",
                null
        );

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
    void shouldSignInAUser() {
        //given
        String password = "originalPassword";
        String hashedPassword = "hashedPassword";
        String generatedToken = "generatedToken";
        User user = new User("pasandevin@gmail.com", "Pasan", "Jayawardene", "student", hashedPassword);
        SignInRequest signInRequest = new SignInRequest(user.getEmail(), password);

        //when
        when(userRepository.findByEmail(any())).thenReturn(user);
        when(userRepository.findByEmail(any())).thenReturn(user);
        when(hashedPasswordGenerator.verify(any(), any())).thenReturn(true);
        when(tokenGenerator.generate(any(), any(), any(), any())).thenReturn(generatedToken);
        ResponseEntity response = underTest.signIn(signInRequest);

        //then
        ArgumentCaptor<String> emailArgumentCaptor0 = ArgumentCaptor.forClass(String.class);
        verify(userRepository, times(6)).findByEmail(emailArgumentCaptor0.capture());
        String capturedEmail0 = emailArgumentCaptor0.getValue();
        assertThat(capturedEmail0).isEqualTo(signInRequest.getEmail());

        ArgumentCaptor<String> passwordArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> hashedPasswordArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(hashedPasswordGenerator).verify(passwordArgumentCaptor.capture(), hashedPasswordArgumentCaptor.capture());
        String capturedPassword = passwordArgumentCaptor.getValue();
        String capturedHashedPassword = hashedPasswordArgumentCaptor.getValue();
        assertThat(capturedPassword).isEqualTo(signInRequest.getPassword());
        assertThat(capturedHashedPassword).isEqualTo(user.getPassword());

        ArgumentCaptor<String> emailArgumentCaptor1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> firstNameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> lastNameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> roleArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(tokenGenerator).generate(emailArgumentCaptor1.capture(), firstNameArgumentCaptor.capture(), lastNameArgumentCaptor.capture(), roleArgumentCaptor.capture());
        String capturedEmail1 = emailArgumentCaptor1.getValue();
        String capturedFirstName = firstNameArgumentCaptor.getValue();
        String capturedLastName = lastNameArgumentCaptor.getValue();
        String capturedRole = roleArgumentCaptor.getValue();
        assertThat(capturedEmail1).isEqualTo(user.getEmail());
        assertThat(capturedFirstName).isEqualTo(user.getFirstName());
        assertThat(capturedLastName).isEqualTo(user.getLastName());
        assertThat(capturedRole).isEqualTo(user.getRole());

        Object body = response.getBody();
        SignInResponse result = (SignInResponse) body;
        assertThat(result.getToken()).isEqualTo(generatedToken);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldThrowWhenEmailIsMissingSignInAUser() {
        //given
        String password = "originalPassword";
        String hashedPassword = "hashedPassword";
        User user = new User(null, "Pasan", "Jayawardene", "student", hashedPassword);
        SignInRequest signInRequest = new SignInRequest(user.getEmail(), password);

        //when
        ResponseEntity response = underTest.signIn(signInRequest);

        //then
        ArgumentCaptor<String> errorMessageCaptor = ArgumentCaptor.forClass(String.class);
        verify(logger).logException(errorMessageCaptor.capture());
        String capturedErrorMessage = errorMessageCaptor.getValue();
        assertThat(capturedErrorMessage).isEqualTo("Email or password is missing");

        verify(userRepository, never()).findByEmail(any());

        verify(hashedPasswordGenerator, never()).verify(any(), any());

        verify(tokenGenerator, never()).generate(any(), any(), any(), any());

        assertThat(response.getStatusCodeValue()).isEqualTo(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void shouldThrowWhenPasswordIsMissingSignInAUser() {
        //given
        String password = null;
        String hashedPassword = "hashedPassword";
        User user = new User("pasandevin@gmail.com", "Pasan", "Jayawardene", "student", hashedPassword);
        SignInRequest signInRequest = new SignInRequest(user.getEmail(), password);

        //when
        ResponseEntity response = underTest.signIn(signInRequest);

        //then
        ArgumentCaptor<String> errorMessageCaptor = ArgumentCaptor.forClass(String.class);
        verify(logger).logException(errorMessageCaptor.capture());
        String capturedErrorMessage = errorMessageCaptor.getValue();
        assertThat(capturedErrorMessage).isEqualTo("Email or password is missing");

        verify(userRepository, never()).findByEmail(any());

        verify(hashedPasswordGenerator, never()).verify(any(), any());

        verify(tokenGenerator, never()).generate(any(), any(), any(), any());

        assertThat(response.getStatusCodeValue()).isEqualTo(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void shouldThrowWhenUserIsNotExistingWhenSigningInAUser() {
        //given
        String password = "originalPassword";
        String hashedPassword = "hashedPassword";
        User user = new User("pasandevin@gmail.com", "Pasan", "Jayawardene", "student", hashedPassword);
        SignInRequest signInRequest = new SignInRequest(user.getEmail(), password);

        //when
        when(userRepository.findByEmail(any())).thenReturn(null);
        ResponseEntity response = underTest.signIn(signInRequest);

        //then
        ArgumentCaptor<String> errorMessageCaptor = ArgumentCaptor.forClass(String.class);
        verify(logger).logException(errorMessageCaptor.capture());
        String capturedErrorMessage = errorMessageCaptor.getValue();
        assertThat(capturedErrorMessage).isEqualTo("User does not exist");

        verify(userRepository, times(1)).findByEmail(any());

        verify(hashedPasswordGenerator, never()).verify(any(), any());

        verify(tokenGenerator, never()).generate(any(), any(), any(), any());

        assertThat(response.getStatusCodeValue()).isEqualTo(HttpServletResponse.SC_UNAUTHORIZED);
    }
}