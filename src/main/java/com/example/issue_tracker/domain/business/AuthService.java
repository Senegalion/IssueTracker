package com.example.issue_tracker.domain.business;

import com.example.issue_tracker.api.dto.AuthRequestDTO;
import com.example.issue_tracker.api.dto.AuthResponseDTO;
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

    public String register(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
        return "User registered successfully";
    }

    public AuthResponseDTO login(AuthRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(userDetails);

        return new AuthResponseDTO(token);
    }
}
