package com.example.issue_tracker.api.controller.rest;

import com.example.issue_tracker.api.dto.AuthRequestDTO;
import com.example.issue_tracker.api.dto.AuthResponseDTO;
import com.example.issue_tracker.api.dto.RegisterRequestDTO;
import com.example.issue_tracker.domain.business.AuthService;
import com.example.issue_tracker.domain.dao.UserDAO;
import com.example.issue_tracker.domain.exceptions.InvalidCredentialsException;
import com.example.issue_tracker.domain.exceptions.InvalidUserException;
import com.example.issue_tracker.infrastructure.security.JwtService;
import com.example.issue_tracker.infrastructure.security.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AuthRestController.class)
@Import(SecurityConfig.class)
class AuthRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AuthRestController(authService))
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    void should_return_200_when_register_successful() throws Exception {
        RegisterRequestDTO requestDTO = new RegisterRequestDTO("John", "Doe", "testUser", "test@example.com", "password");
        when(authService.register(any(UserDAO.class))).thenReturn("User registered successfully");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));
    }

    @Test
    void should_return_400_when_register_invalid_input() throws Exception {
        RegisterRequestDTO requestDTO = new RegisterRequestDTO(null, null, "testUser", "test@example.com", "password");
        when(authService.register(any(UserDAO.class))).thenThrow(new InvalidUserException("User has invalid data"));  // Using Mockito's any()

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_return_token_when_login_successful() throws Exception {
        AuthRequestDTO requestDTO = new AuthRequestDTO("testUser", "password");
        AuthResponseDTO responseDTO = new AuthResponseDTO("mockToken");
        when(authService.login(any(AuthRequestDTO.class))).thenReturn(responseDTO);  // Using Mockito's any()

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mockToken"));
    }

    @Test
    void should_return_401_when_login_fails() throws Exception {
        AuthRequestDTO requestDTO = new AuthRequestDTO("testUser", "wrongPassword");
        when(authService.login(any(AuthRequestDTO.class))).thenThrow(new InvalidCredentialsException("Invalid credentials"));  // Using Mockito's any()

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isUnauthorized());
    }
}
