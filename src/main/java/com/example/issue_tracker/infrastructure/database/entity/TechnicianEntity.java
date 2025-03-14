package com.example.issue_tracker.infrastructure.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "technicians")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TechnicianEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "technician_id")
    private Long technicianId;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Surname cannot be empty")
    private String surname;

    @NotEmpty(message = "Skillset cannot be empty")
    @Column(columnDefinition = "TEXT")
    private String skillset;

    @OneToMany(mappedBy = "technician", fetch = FetchType.LAZY)
    private Set<IssueEntity> issues;
}
