package com.example.issue_tracker.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequestDTO {
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
}
