package com.survey.backend.controller;

import com.survey.backend.model.SurveyForm;
import com.survey.backend.service.SurveyFormService;
import com.survey.backend.service.SurveyInviteService; // Import the SurveyInviteService to send emails

import main.java.com.survey.backend.dto.CreateSurveyRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/surveys")
@CrossOrigin(origins = "http://localhost:3000")
public class SurveyFormController {

    private final SurveyFormService service;
    private final SurveyInviteService surveyInviteService; // Inject the SurveyInviteService

    // Constructor with SurveyInviteService injected
    public SurveyFormController(SurveyFormService service, SurveyInviteService surveyInviteService) {
        this.service = service;
        this.surveyInviteService = surveyInviteService;
    }

    // Get all surveys
    @GetMapping
    public List<SurveyForm> getAllSurveys() {
        return service.getAllSurveys(); // Fetch all surveys
    }

    // Create a new survey and send invites
    @PostMapping
public ResponseEntity<SurveyForm> createSurvey(@RequestBody CreateSurveyRequest request) {
    SurveyForm createdSurvey = service.createSurvey(request);

    // Send invites
    List<String> emails = request.getEmails();
    if (emails != null && !emails.isEmpty()) {
        surveyInviteService.sendInvites(createdSurvey.getId(), emails);
    }

    return ResponseEntity.ok(createdSurvey);
}


    // Update an existing survey
    @PutMapping("/{id}")
    public ResponseEntity<SurveyForm> updateSurvey(@PathVariable Long id, @RequestBody SurveyForm form) {
        return ResponseEntity.ok(service.updateSurvey(id, form)); // Update the survey
    }

    // Get details of a specific survey by ID
    @GetMapping("/{id}")
    public ResponseEntity<SurveyForm> getSurvey(@PathVariable Long id) {
        return ResponseEntity.ok(service.getSurvey(id)); // Fetch survey by ID
    }
}
