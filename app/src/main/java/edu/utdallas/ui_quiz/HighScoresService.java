package edu.utdallas.ui_quiz;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HighScoresService {
    private static final String fileName = "highScores.txt";
    private Context context;
    private ArrayList<HighScore> highScores;
    private File file;

    public HighScoresService(Context context) {
        this.context = context;
        this.highScores = new ArrayList<>();
        this.file = new File(context.getFilesDir(), fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String split[] = line.split("\t");
                HighScore score = new HighScore(split[0], Integer.parseInt(split[1]));
                this.highScores.add(score);
            }

            this.sort();

        } catch (IOException e) {

        }
    }

    public void addHighScore(HighScore highScore) {
        this.highScores.add(highScore);
        this.sort();
        while(this.highScores.size() > 5) {
            this.highScores.remove(5);
        }
    }

    private void writeToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for(HighScore h: highScores) {
                bw.write(h.toString());
                bw.newLine();
            }
        } catch (IOException e) {

        }
    }

    public ArrayList<HighScore> getHighScores() {
        return this.highScores;
    }

    private void sort() {
        Collections.sort(highScores, new Comparator<HighScore>() {
            @Override
            public int compare(HighScore h1, HighScore h2) {
                return (new Integer(h1.getScore())).compareTo(new Integer(h2.getScore()));
            }
        });
    }
}
