package edu.utdallas.ui_quiz;

// Dominic Joseph - dxj120030
// Steven Hogue - sdh140330
// HighScore
// Class to hold score and associated name
//
// Primary author(s): Steven
public class HighScore {
    private String name;
    private int score;

    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }

    public String toString() {
        return this.name + "\t" + this.score;
    }
}
