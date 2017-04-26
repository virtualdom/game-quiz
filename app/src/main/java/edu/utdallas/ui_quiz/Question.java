package edu.utdallas.ui_quiz;

// Dominic Joseph - dxj120030
// Steven Hogue - sdh140330
// Question class
// This class represents a single question,
// its answer, and any additional information.
//
// Primary author(s): Dominic
public class Question {
    private String question;
    private boolean answer;
    private String additionalInfo;

    // Various constructors
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

    public Question (String question, String answer) {
        this.question = question;
        this.additionalInfo = "Thanks for submitting this question!";

        if (answer.toLowerCase().trim().equals("t")) this.answer = true;
        else this.answer = false;
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

    // Serializer method
    public String toString () { return question + "\t" + (answer ? "t" : "f") + "\t" + additionalInfo; }

    // Accessor methods
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
