package com.survey.backend.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class SurveyForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String department; // Optional: For department-wise survey

    @OneToMany(mappedBy = "surveyForm", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Question> questions = new ArrayList<>();  // Initialize list

    // Declare emails field
    @ElementCollection
    private List<String> emails = new ArrayList<>();  // Initialize list

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    // Helper methods to manage the list of questions
    public void addQuestion(Question question) {
        questions.add(question);
        question.setSurveyForm(this);  // Ensure bidirectional relationship
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
        question.setSurveyForm(null);  // Ensure bidirectional relationship
    }
}
