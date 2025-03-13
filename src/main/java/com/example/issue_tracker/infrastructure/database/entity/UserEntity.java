package com.example.issue_tracker.infrastructure.database.entity;

import com.example.issue_tracker.infrastructure.database.entity.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotEmpty(message = "Name cannot be empty")
    @Size(max = 50)
    private String name;

    @NotEmpty(message = "Surname cannot be empty")
    @Size(max = 50)
    private String surname;

    @NotEmpty(message = "Username cannot be empty")
    @Size(max = 50)
    @Column(unique = true)
    private String username;

    @NotEmpty(message = "Email cannot be empty")
    @Email
    @Size(max = 100)
    @Column(unique = true)
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @NotEmpty(message = "Role cannot be empty")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private Timestamp createdAt;
}
