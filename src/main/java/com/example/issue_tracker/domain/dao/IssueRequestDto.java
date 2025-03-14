package com.example.issue_tracker.domain.dao;

import com.example.issue_tracker.infrastructure.database.entity.IssueStatus;
import com.example.issue_tracker.infrastructure.database.entity.TechnicianEntity;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class IssueRequestDto {
    private String description;
    private IssueStatus status;
    private String location;
    private String latitude;
    private String longitude;
    private TechnicianEntity technician;
}
