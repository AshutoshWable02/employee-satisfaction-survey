package com.survey.backend.service;

import com.survey.backend.dto.CreateSurveyRequest;
import com.survey.backend.dto.QuestionRequest;
import com.survey.backend.model.Question;
import com.survey.backend.model.SurveyForm;
import com.survey.backend.model.QuestionType;
import com.survey.backend.repository.SurveyFormRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SurveyFormService {

    private final SurveyFormRepository repository;

    public SurveyFormService(SurveyFormRepository repository) {
        this.repository = repository;
    }

    public List<SurveyForm> getAllSurveys() {
        return repository.findAll();
    }

    @Transactional
    public SurveyForm createSurvey(CreateSurveyRequest request) {
        SurveyForm surveyForm = new SurveyForm();
        surveyForm.setTitle(request.getTitle());
        surveyForm.setDescription(request.getDescription());
        surveyForm.setDepartment(request.getDepartment());
        surveyForm.setEmails(request.getEmails());

        List<Question> questions = new ArrayList<>();
        for (QuestionRequest questionRequest : request.getQuestions()) {
            Question question = new Question();
            question.setText(questionRequest.getText());
            question.setType(QuestionType.valueOf(questionRequest.getType().toUpperCase()));

            // Only set options if it's an MCQ
            if (questionRequest.getType().equals("MCQ")) {
                question.setOptions(questionRequest.getOptions());
            }

            question.setSurveyForm(surveyForm);  // Set the parent relationship
            questions.add(question);
        }

        surveyForm.setQuestions(questions);

        return repository.save(surveyForm);  // Cascade will save questions
    }

    @Transactional
    public SurveyForm updateSurvey(Long id, SurveyForm updatedForm) {
        SurveyForm existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Survey Form not found with id " + id));

        existing.setTitle(updatedForm.getTitle());
        existing.setDescription(updatedForm.getDescription());
        existing.setDepartment(updatedForm.getDepartment());

        // Update questions only if present
        if (updatedForm.getQuestions() != null && !updatedForm.getQuestions().isEmpty()) {
            // Clear existing questions, add new ones
            existing.getQuestions().clear();
            updatedForm.getQuestions().forEach(q -> {
                q.setSurveyForm(existing); // Set the parent relation
                existing.getQuestions().add(q);
            });
        }

        return repository.save(existing);
    }

    // Fetch a single survey form by ID
    public SurveyForm getSurvey(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey not found with id: " + id));
    }

    // Delete a survey by ID
    @Transactional
    public void deleteSurvey(Long id) {
        SurveyForm surveyForm = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey not found with id: " + id));

        // Optionally, you can also delete questions associated with the survey
        // if cascade delete is not configured in the relationship.
        // If cascade delete is enabled, the associated questions will be deleted automatically.

        repository.delete(surveyForm);
    }
}
