package com.survey.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SurveyInvite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String department;
    private String password;
    private boolean used = false;

    private LocalDateTime createdAt;

    private Long surveyId;

    @ManyToOne
    private SurveyForm surveyForm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public SurveyForm getSurveyForm() {
        return surveyForm;
    }

    public void setSurveyForm(SurveyForm surveyForm) {
        this.surveyForm = surveyForm;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }
    // Getters and Setters
}
