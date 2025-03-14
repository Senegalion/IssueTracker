package com.example.issue_tracker.api.dto.mapper;

import com.example.issue_tracker.api.dto.IssueCreateRequestDTO;
import com.example.issue_tracker.api.dto.IssueResponseDTO;
import com.example.issue_tracker.domain.dao.IssueRequestDAO;
import com.example.issue_tracker.domain.dao.IssueResponseDAO;

public class IssueDTOMapper {
    public static IssueResponseDTO mapFromIssueResponseDAO(IssueResponseDAO issueResponseDAO) {
        return IssueResponseDTO.builder()
                .issueId(issueResponseDAO.getIssueId())
                .message(issueResponseDAO.getMessage())
                .build();
    }

    public static IssueRequestDAO mapToIssueRequestDAO(IssueCreateRequestDTO issueCreateRequestDTO) {
        return IssueRequestDAO.builder()
                .description(issueCreateRequestDTO.getDescription())
                .latitude(issueCreateRequestDTO.getLatitude())
                .longitude(issueCreateRequestDTO.getLongitude())
                .build();
    }
}
