package com.example.radek.quiz;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable{
    private String content;
    private int difficulty;
    private List<String> answers;
    private int correctAnswers;


    public Question(String content, int difficulty, List<String> answers, int correctAnswers) {
        this.content = content;
        this.difficulty = difficulty;
        this.answers = answers;
        this.correctAnswers = correctAnswers;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
}
