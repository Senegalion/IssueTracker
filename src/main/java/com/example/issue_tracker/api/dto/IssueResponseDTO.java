package com.example.issue_tracker.api.dto;

import lombok.Builder;

@Builder
public class IssueResponseDTO {
    private Long issueId;
    private String message;
}
