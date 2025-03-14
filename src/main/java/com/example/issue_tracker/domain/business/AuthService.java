package com.example.issue_tracker.domain.business;

import com.example.issue_tracker.api.dto.AuthRequestDTO;
import com.example.issue_tracker.api.dto.AuthResponseDTO;
import com.example.issue_tracker.api.dto.RegisterRequestDTO;
import com.example.issue_tracker.infrastructure.database.entity.UserEntity;
import com.example.issue_tracker.infrastructure.database.repository.jpa.UserRepository;
import com.example.issue_tracker.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(RegisterRequestDTO request) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(request.getName());
        userEntity.setSurname(request.getSurname());
        userEntity.setUsername(request.getUsername());
        userEntity.setEmail(request.getEmail());
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(userEntity);
        return "User registered successfully";
    }

    public AuthResponseDTO login(AuthRequestDTO request) {
        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(request.getUsername());

        if (userEntityOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        UserEntity userEntity = userEntityOptional.get();
        if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(userEntity);
        return new AuthResponseDTO(token);
    }
}
