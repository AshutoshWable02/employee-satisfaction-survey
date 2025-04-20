package com.survey.backend.service;

import main.java.com.survey.backend.dto.*;
//import com.survey.backend.dto.QuestionRequest;
import main.java.com.survey.backend.model.Question;
import main.java.com.survey.backend.model.SurveyForm;
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
            question.setType(questionRequest.getType());

            // Only set options if it's an MCQ
            if (questionRequest.getType().toString().equals("MCQ")) {
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

        // Update only questions, keep existing questions intact if not updated
        List<Question> updatedQuestions = updatedForm.getQuestions();
        if (updatedQuestions != null && !updatedQuestions.isEmpty()) {
            existing.getQuestions().clear();  // Clear existing questions

            updatedQuestions.forEach(q -> {
                q.setSurveyForm(existing);
                existing.getQuestions().add(q);
            });
        }

        return repository.save(existing);
    }

    public SurveyForm getSurvey(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey not found with id: " + id));
    }
}
