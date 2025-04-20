
package com.survey.backend.service;

import com.survey.backend.dto.CreateSurveyRequest;
import com.survey.backend.dto.QuestionRequest;
import com.survey.backend.model.Question;
import com.survey.backend.model.QuestionType;
import com.survey.backend.model.SurveyForm;
import com.survey.backend.repository.SurveyFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SurveyService {

    @Autowired
    private SurveyFormRepository surveyFormRepository;

    public SurveyForm createSurvey(CreateSurveyRequest request) {
        SurveyForm surveyForm = new SurveyForm();
        surveyForm.setTitle(request.getTitle());
        surveyForm.setDescription(request.getDescription());
        surveyForm.setDepartment(request.getDepartment());
        surveyForm.setEmails(request.getEmails());

        List<Question> questions = new ArrayList<>();
        for (QuestionRequest qr : request.getQuestions()) {
            Question question = new Question();
            question.setText(qr.getText());
            question.setType(QuestionType.valueOf(qr.getType()));

            // Only set options if it's an MCQ
            if (qr.getType().equals(QuestionType.MCQ.name())) {
                question.setOptions(qr.getOptions());
            }

            question.setSurveyForm(surveyForm); // Set the parent
            questions.add(question);
        }

        surveyForm.setQuestions(questions);

        return surveyFormRepository.save(surveyForm);  // Cascade will also save questions
    }
}
