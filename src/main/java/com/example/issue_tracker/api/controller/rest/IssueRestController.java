package com.example.issue_tracker.api.controller.rest;

import com.example.issue_tracker.api.dto.IssueCreateRequestDTO;
import com.example.issue_tracker.api.dto.IssueResponseDTO;
import com.example.issue_tracker.api.dto.mapper.IssueDTOMapper;
import com.example.issue_tracker.domain.business.IssueService;
import com.example.issue_tracker.domain.dao.IssueRequestDAO;
import com.example.issue_tracker.domain.dao.IssueResponseDAO;
import com.example.issue_tracker.infrastructure.database.entity.IssueStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/issues")
@AllArgsConstructor
public class IssueRestController {
    private final IssueService issueService;

    @PostMapping
    public ResponseEntity<IssueResponseDTO> createIssue(@RequestBody IssueCreateRequestDTO request) {
        IssueRequestDAO issueRequestDAO = IssueDTOMapper.mapToIssueRequestDAO(request);
        IssueResponseDAO response = issueService.createIssue(issueRequestDAO);
        IssueResponseDTO issueResponseDTO = IssueDTOMapper.mapFromIssueResponseDAO(response);
        return ResponseEntity.ok(issueResponseDTO);
    }

    @PutMapping("/{issueId}/assign/{technicianId}")
    public ResponseEntity<IssueResponseDTO> assignTechnician(@PathVariable Long issueId, @PathVariable Long technicianId) {
        IssueResponseDAO response = issueService.assignTechnician(issueId, technicianId);
        IssueResponseDTO issueResponseDTO = IssueDTOMapper.mapFromIssueResponseDAO(response);
        return ResponseEntity.ok(issueResponseDTO);
    }

    @PatchMapping("/{issueId}/status")
    public ResponseEntity<IssueResponseDTO> changeIssueStatus(@PathVariable Long issueId, @RequestParam IssueStatus status) {
        IssueResponseDAO response = issueService.changeIssueStatus(issueId, status);
        IssueResponseDTO issueResponseDTO = IssueDTOMapper.mapFromIssueResponseDAO(response);
        return ResponseEntity.ok(issueResponseDTO);
    }
}
