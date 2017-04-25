package edu.utdallas.ui_quiz;


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
