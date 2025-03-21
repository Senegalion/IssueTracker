package com.example.issue_tracker.domain.business;

import com.example.issue_tracker.domain.dao.IssueRequestDAO;
import com.example.issue_tracker.domain.dao.IssueResponseDAO;
import com.example.issue_tracker.domain.dao.mapper.IssueDAOMapper;
import com.example.issue_tracker.domain.exceptions.IssueNotFoundException;
import com.example.issue_tracker.domain.exceptions.TechnicianNotFoundException;
import com.example.issue_tracker.infrastructure.database.entity.IssueEntity;
import com.example.issue_tracker.infrastructure.database.entity.IssueStatus;
import com.example.issue_tracker.infrastructure.database.entity.TechnicianEntity;
import com.example.issue_tracker.infrastructure.database.repository.jpa.IssueRepository;
import com.example.issue_tracker.infrastructure.database.repository.jpa.TechnicianRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IssueService {
    private final IssueRepository issueRepository;
    private final TechnicianRepository technicianRepository;
//    private final GoogleMapsService googleMapsService;

    public IssueResponseDAO createIssue(IssueRequestDAO request) {
//        String location = googleMapsService.getAddressFromCoordinates(request.getLatitude(), request.getLongitude());
        String location = "some location";

        IssueEntity issue = IssueDAOMapper.mapToIssueEntity(request);

        IssueEntity savedIssue = issueRepository.save(issue);
        return IssueDAOMapper.mapFromIssueEntity(savedIssue);
    }

    public IssueResponseDAO assignTechnician(Long issueId, Long technicianId) {
        IssueEntity issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IssueNotFoundException(
                        String.format("Issue with id: [%s] not found", issueId)
                ));

        TechnicianEntity technician = technicianRepository.findById(technicianId)
                .orElseThrow(() -> new TechnicianNotFoundException(
                        String.format("Technician with id: [%s] not found", technicianId)
                ));

        issue.setTechnician(technician);
        issue.setStatus(IssueStatus.IN_PROGRESS);

        issueRepository.save(issue);

        return new IssueResponseDAO(issue.getIssueId(), String.format("Technician with id: [%d] has been assigned to your issue", technicianId));
    }

    public IssueResponseDAO changeIssueStatus(Long issueId, IssueStatus status) {
        IssueEntity issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IssueNotFoundException(
                        String.format("Issue with id: [%s] not found", issueId))
                );

        issue.setStatus(status);
        issueRepository.save(issue);

        return new IssueResponseDAO(issue.getIssueId(), "Issue with id");
    }
}
