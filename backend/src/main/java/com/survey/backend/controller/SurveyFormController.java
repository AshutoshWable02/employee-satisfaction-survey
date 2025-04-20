package com.survey.backend.controller;

import com.survey.backend.model.SurveyForm;
import com.survey.backend.service.SurveyFormService;
import com.survey.backend.service.SurveyInviteService;
import com.survey.backend.dto.CreateSurveyRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/surveys")
@CrossOrigin(origins = "http://localhost:3000")
public class SurveyFormController {

    private final SurveyFormService service;
    private final SurveyInviteService surveyInviteService;

    // Constructor with SurveyInviteService injected
    public SurveyFormController(SurveyFormService service, SurveyInviteService surveyInviteService) {
        this.service = service;
        this.surveyInviteService = surveyInviteService;
    }

    // Get all surveys
    @GetMapping
    public ResponseEntity<List<SurveyForm>> getAllSurveys() {
        try {
            List<SurveyForm> surveys = service.getAllSurveys();
            return ResponseEntity.ok(surveys); // HTTP 200 with the surveys list
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Create a new survey and send invites
    @PostMapping
    public ResponseEntity<SurveyForm> createSurvey(@RequestBody CreateSurveyRequest request) {
        try {
            SurveyForm createdSurvey = service.createSurvey(request);

            // Send invites
            List<String> emails = request.getEmails();
            if (emails != null && !emails.isEmpty()) {
                surveyInviteService.sendInvites(createdSurvey.getId(), emails);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(createdSurvey); // HTTP 201 for creation
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Update an existing survey
    @PutMapping("/{id}")
    public ResponseEntity<SurveyForm> updateSurvey(@PathVariable Long id, @RequestBody SurveyForm form) {
        try {
            SurveyForm updatedSurvey = service.updateSurvey(id, form);
            return ResponseEntity.ok(updatedSurvey); // HTTP 200 with updated survey
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Get details of a specific survey by ID
    @GetMapping("/{id}")
    public ResponseEntity<SurveyForm> getSurvey(@PathVariable Long id) {
        try {
            SurveyForm survey = service.getSurvey(id);
            return ResponseEntity.ok(survey); // HTTP 200 with the survey details
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Delete a survey
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSurvey(@PathVariable Long id) {
        try {
            service.deleteSurvey(id);
            return ResponseEntity.ok("Survey deleted successfully!"); // HTTP 200 on success
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting survey.");
        }
    }

    // Handle exceptions globally for the controller
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
    }
}
