package com.example.radek.quiz;

import java.util.List;

public interface QuestionDatabase {
    List<Question> getQuestions(int difficulty);
}
