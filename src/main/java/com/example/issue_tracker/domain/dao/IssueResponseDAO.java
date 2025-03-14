package com.example.issue_tracker.domain.dao;

import com.example.issue_tracker.infrastructure.database.entity.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class IssueResponseDAO {
    private Long issueId;
    private String description;
    private String location;
    private IssueStatus status;
}
