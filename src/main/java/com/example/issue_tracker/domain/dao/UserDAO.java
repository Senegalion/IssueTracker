package com.example.issue_tracker.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserDAO {
    private Long userId;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
}
