package com.example.issue_tracker.api.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class IssueCreateRequestDTO {
    private String description;
    private String latitude;
    private String longitude;
}
