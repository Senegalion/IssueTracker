package com.example.issue_tracker.domain.business;

import com.example.issue_tracker.domain.dao.IssueRequestDto;
import com.example.issue_tracker.domain.dao.IssueResponseDAO;
import com.example.issue_tracker.domain.exceptions.IssueNotFoundException;
import com.example.issue_tracker.domain.exceptions.TechnicianNotFoundException;
import com.example.issue_tracker.infrastructure.database.entity.IssueEntity;
import com.example.issue_tracker.infrastructure.database.entity.IssueStatus;
import com.example.issue_tracker.infrastructure.database.entity.TechnicianEntity;
import com.example.issue_tracker.infrastructure.database.repository.jpa.IssueRepositoryTestImpl;
import com.example.issue_tracker.infrastructure.database.repository.jpa.TechnicianRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IssueServiceTest {
    private IssueRepositoryTestImpl issueRepository;
    private TechnicianRepository technicianRepository;
    private IssueService issueService;

    @BeforeEach
    void setUp() {
        issueRepository = new IssueRepositoryTestImpl();
        technicianRepository = mock(TechnicianRepository.class);
        issueService = new IssueService(issueRepository, technicianRepository);
    }

    @Test
    void should_create_issue_successfully() {
        // given
        IssueRequestDto request = IssueRequestDto.builder()
                .description("Test issue")
                .latitude("52.2298")
                .longitude("21.0122")
                .build();

        // when
        IssueResponseDAO response = issueService.createIssue(request);

        // then
        assertEquals(1L, response.getIssueId());
        assertEquals("Issue with id: [1] has been created", response.getMessage());

        IssueEntity savedIssue = issueRepository.findById(1L).orElseThrow();
        assertEquals("Test issue", savedIssue.getDescription());
        assertEquals(IssueStatus.NEW, savedIssue.getStatus());
    }

    @Test
    void should_assign_technician_to_issue_successfully() {
        // given
        IssueEntity issue = issueRepository.save(
                IssueEntity.builder()
                        .issueId(1L)
                        .description("Test issue")
                        .status(IssueStatus.NEW)
                        .location("location")
                        .latitude("52.2298")
                        .longitude("21.0122")
                        .build());
        TechnicianEntity technician = new TechnicianEntity();
        technician.setTechnicianId(2L);

        when(technicianRepository.findById(2L)).thenReturn(Optional.of(technician));

        // when
        IssueResponseDAO response = issueService.assignTechnician(issue.getIssueId(), 2L);

        // then
        assertEquals(issue.getIssueId(), response.getIssueId());
        assertEquals("Technician with id: [2] has been assigned to your issue", response.getMessage());

        IssueEntity updatedIssue = issueRepository.findById(issue.getIssueId()).orElseThrow();
        assertEquals(IssueStatus.IN_PROGRESS, updatedIssue.getStatus());
        assertNotNull(updatedIssue.getTechnician());
    }

    @Test
    void should_throw_IssueNotFoundException_when_assigning_non_existing_issue() {
        // given
        Long nonExistingIssueId = 99L;
        Long technicianId = 2L;

        // when & then
        assertThrows(IssueNotFoundException.class, () -> issueService.assignTechnician(nonExistingIssueId, technicianId));
    }

    @Test
    void should_throw_TechnicianNotFoundException_when_assigning_non_existing_technician() {
        // given
        Long issueId = 1L;
        Long technicianId = 2L;

        IssueEntity issue = issueRepository.save(
                IssueEntity.builder()
                        .issueId(issueId)
                        .description("Test issue")
                        .status(IssueStatus.NEW)
                        .location("location")
                        .latitude("52.2298")
                        .longitude("21.0122")
                        .build());

        when(technicianRepository.findById(technicianId)).thenReturn(Optional.empty());

        // when
        // then
        assertThrows(TechnicianNotFoundException.class, () -> issueService.assignTechnician(issueId, technicianId));
        verify(technicianRepository, times(1)).findById(technicianId);
    }

    @Test
    void should_change_issue_status_successfully() {
        // given
        IssueEntity issue = issueRepository.save(
                IssueEntity.builder()
                        .issueId(1L)
                        .description("Test issue")
                        .status(IssueStatus.NEW)
                        .location("location")
                        .latitude("52.2298")
                        .longitude("21.0122")
                        .build());

        // when
        IssueResponseDAO response = issueService.changeIssueStatus(issue.getIssueId(), IssueStatus.DONE);

        // then
        assertEquals(issue.getIssueId(), response.getIssueId());

        IssueEntity updatedIssue = issueRepository.findById(issue.getIssueId()).orElseThrow();
        assertEquals(IssueStatus.DONE, updatedIssue.getStatus());
    }

    @Test
    void should_throw_IssueNotFoundException_when_changing_status_of_non_existing_issue() {
        // given
        Long issueId = 1L;
        IssueStatus newStatus = IssueStatus.DONE;

        // when
        // then
        assertThrows(IssueNotFoundException.class, () -> issueService.changeIssueStatus(issueId, newStatus));
    }
}