package edu.utdallas.ui_quiz;

import android.content.Context;
import android.content.res.Resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class QuestionsController {
    private QuestionsServices service;
    private ArrayList<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    public QuestionsController (Resources resources, Context c) {
        service = new QuestionsServices(resources, c);
        try {
            questions = service.getQuestions();
        } catch (IOException ex) {
            questions = new ArrayList<Question>();
            questions.add(new Question("Something broke! We lost all own questions. Please restart the app.", true, "We said restart the app!"));
        }
    }

    public void reset () {
        currentQuestionIndex = 0;
        score = 0;
        Collections.shuffle(questions);
    }

    public boolean addQuestion (String question, String answer) {
        try {
            service.addQuestion(new Question(question, answer));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Question getQuestion () {
        return questions.get(currentQuestionIndex);
    }

    public int getScore() {
        return score;
    }

    public boolean answerQuestion(boolean answer) {
        if (answer == questions.get(currentQuestionIndex).getAnswer()) {
            this.score += 10;
            if (++currentQuestionIndex >= questions.size()) {
                currentQuestionIndex = 0;
                Collections.shuffle(questions);
            }
            return true;
        } else {
            return false;
        }
    }
}
