package com.example.issue_tracker.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class IssueResponseDAO {
    private Long issueId;
    private String message;
}
