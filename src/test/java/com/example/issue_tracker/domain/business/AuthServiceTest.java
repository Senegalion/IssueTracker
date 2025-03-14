package com.example.issue_tracker.domain.business;

import com.example.issue_tracker.api.dto.AuthRequestDTO;
import com.example.issue_tracker.api.dto.AuthResponseDTO;
import com.example.issue_tracker.domain.dao.UserDAO;
import com.example.issue_tracker.domain.exceptions.InvalidCredentialsException;
import com.example.issue_tracker.domain.exceptions.InvalidUserException;
import com.example.issue_tracker.infrastructure.database.entity.UserEntity;
import com.example.issue_tracker.infrastructure.database.repository.jpa.UserRepository;
import com.example.issue_tracker.infrastructure.security.JwtService;
import com.example.issue_tracker.infrastructure.security.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    private AuthService authService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private UserDetailsServiceImpl userDetailsService;


    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtService = mock(JwtService.class);
        authenticationManager = mock(AuthenticationManager.class);
        userDetailsService = mock(UserDetailsServiceImpl.class);

        authService = new AuthService(passwordEncoder, authenticationManager, jwtService, userDetailsService, userRepository);
    }

    @Test
    void should_return_user_registered_successfully_when_user_had_registered() {
        // given
        UserDAO userDAO = new UserDAO(1L, "John", "Doe", "testUser", "test@example.com", "password");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));


        // when
        String result = authService.register(userDAO);

        // then
        assertEquals("User registered successfully", result);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void should_throw_invalid_register_input_exception_when_user_dao_has_null_fields() {
        // given
        UserDAO invalidUserDao = UserDAO.builder().build();

        // when
        // then
        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class, () -> {
            authService.register(invalidUserDao);
        });
        assertEquals("User has invalid data", invalidUserException.getMessage());
    }

    @Test
    void should_return_token_when_user_logs_in() {
        // given
        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(auth);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);
        when(jwtService.generateToken(any())).thenReturn("mockToken");

        // when
        AuthResponseDTO authResponseDTO = authService.login(new AuthRequestDTO("testUser", "password"));

        // then
        assertNotNull(authResponseDTO.getToken());
        assertEquals("mockToken", authResponseDTO.getToken());
    }

    @Test
    void should_throw_invalid_credentials_exception_when_user_gave_invalid_password() {
        // given
        when(authenticationManager.authenticate(any())).thenThrow(InvalidCredentialsException.class);

        // when
        // then
        InvalidCredentialsException invalidCredentialsException = assertThrows(InvalidCredentialsException.class, () -> {
            authService.login(new AuthRequestDTO("testUser", "wrongPassword"));
        });
        assertEquals("You entered invalid password for username: [testUser]", invalidCredentialsException.getMessage());
    }

    @Test
    void should_throw_exception_when_user_already_exists() {
        // given
        UserEntity existingUser = UserEntity.builder().username("testUser").email("test@example.com").build();
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(existingUser));

        UserDAO userDAO = new UserDAO(1L, "John", "Doe", "testUser", "test@example.com", "password");

        // when & then
        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> authService.register(userDAO));
        assertEquals("User with given username already exists", exception.getMessage());
    }

    @Test
    void should_throw_exception_when_email_already_exists() {
        // given
        UserEntity existingUser = UserEntity.builder().username("otherUser").email("test@example.com").build();
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));

        UserDAO userDAO = new UserDAO(1L, "John", "Doe", "testUser", "test@example.com", "password");

        // when & then
        InvalidUserException exception = assertThrows(InvalidUserException.class, () -> authService.register(userDAO));
        assertEquals("User with given email already exists", exception.getMessage());
    }

    @Test
    void should_throw_exception_when_token_generation_fails() {
        // given
        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(mock(UserDetails.class));
        when(jwtService.generateToken(any())).thenThrow(new RuntimeException("JWT error"));

        // when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login(new AuthRequestDTO("testUser", "password"));
        });

        // then
        assertEquals("JWT error", exception.getMessage());
    }
}
