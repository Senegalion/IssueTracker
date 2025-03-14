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
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserRepository userRepository;

    public String register(UserDAO userDAO) {
        if (userDAO == null ||
                userDAO.getSurname() == null ||
                userDAO.getUsername() == null ||
                userDAO.getEmail() == null ||
                userDAO.getPassword() == null) {
            throw new InvalidUserException("User has invalid data");
        }

        if (userRepository.findByUsername(userDAO.getUsername()).isPresent()) {
            throw new InvalidUserException("User with given username already exists");
        }

        if (userRepository.findByEmail(userDAO.getEmail()).isPresent()) {
            throw new InvalidUserException("User with given email already exists");
        }

        UserEntity userEntity = UserEntity.builder()
                .name(userDAO.getName())
                .surname(userDAO.getSurname())
                .username(userDAO.getUsername())
                .email(userDAO.getEmail())
                .password(passwordEncoder.encode(userDAO.getPassword()))
                .build();

        userRepository.save(userEntity);
        return "User registered successfully";
    }

    public AuthResponseDTO login(AuthRequestDTO request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword()));
        } catch (InvalidCredentialsException exception) {
            throw new InvalidCredentialsException(
                    String.format("You entered invalid password for username: [%s]",
                            request.getUsername())
            );
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(userDetails);

        return new AuthResponseDTO(token);
    }
}
