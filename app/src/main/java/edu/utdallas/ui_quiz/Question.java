package edu.utdallas.ui_quiz;

public class Question {
    private String question;
    private boolean answer;
    private String additionalInfo;

    public Question () {
        question = "";
        answer = false;
        additionalInfo = "";
    }

    public Question (String question, boolean answer) {
        this.question = question;
        this.answer = answer;
        this.additionalInfo = "Thanks for submitting this question!";
    }

    public Question (String question, boolean answer, String additionalInfo) {
        this.question = question;
        this.answer = answer;
        this.additionalInfo = additionalInfo;
    }

    public Question (String question, String answer, String additionalInfo) {
        this.question = question;
        this.additionalInfo = additionalInfo;

        if (answer.toLowerCase().trim().equals("t")) this.answer = true;
        else this.answer = false;
    }

    public String getAdditionalInfo () {
        return additionalInfo;
    }

    public String getQuestion () {
        return question;
    }

    public boolean getAnswer () {
        return answer;
    }
}
