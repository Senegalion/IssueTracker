package com.example.issue_tracker.domain.dao;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class IssueRequestDto {
    private String description;
    private String location;
    private String latitude;
    private String longitude;
}
