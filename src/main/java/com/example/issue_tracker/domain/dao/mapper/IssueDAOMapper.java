package com.example.issue_tracker.domain.dao.mapper;

import com.example.issue_tracker.domain.dao.IssueRequestDAO;
import com.example.issue_tracker.domain.dao.IssueResponseDAO;
import com.example.issue_tracker.infrastructure.database.entity.IssueEntity;
import com.example.issue_tracker.infrastructure.database.entity.IssueStatus;

public class IssueDAOMapper {
    public static IssueEntity mapToIssueEntity(IssueRequestDAO issueRequestDAO) {
        return IssueEntity.builder()
                .description(issueRequestDAO.getDescription())
                .status(IssueStatus.NEW)
                .location(issueRequestDAO.getLocation())
                .latitude(issueRequestDAO.getLatitude())
                .longitude(issueRequestDAO.getLongitude())
                .build();
    }

    public static IssueResponseDAO mapFromIssueEntity(IssueEntity issueEntity) {
        return IssueResponseDAO.builder()
                .issueId(issueEntity.getIssueId())
                .message(String.format("Issue with id: [%d] has been created", issueEntity.getIssueId()))
                .build();
    }
}
