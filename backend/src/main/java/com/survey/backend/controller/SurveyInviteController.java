package com.survey.backend.controller;

import com.survey.backend.service.SurveyInviteService;
//import com.survey.backend.service.SurveyInviteService.InviteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.survey.backend.dto.InviteRequest;

import java.util.List;

@RestController
@RequestMapping("/api/invite")
public class SurveyInviteController {

    @Autowired
    private SurveyInviteService inviteService;

    @PostMapping("/send/{surveyId}")
    public ResponseEntity<?> sendSurveyInvite(@PathVariable Long surveyId, @RequestBody InviteRequest inviteRequest) {
    List<String> emails = inviteRequest.getEmails();
    inviteService.sendInvites(surveyId, emails); // <-- This is where service handles email sending
    return ResponseEntity.ok("Invites sent successfully.");
    }

}
