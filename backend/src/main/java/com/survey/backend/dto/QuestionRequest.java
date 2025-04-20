package main.java.com.survey.backend.dto;

import com.survey.backend.model.QuestionType;
import java.util.List;

public class QuestionRequest {
    private String text;
    private QuestionType type;
    private List<String> options; // optional for TEXT and RATING

    // Getters and Setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
