package edu.utdallas.ui_quiz;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// Dominic Joseph - dxj120030
// Steven Hogue - sdh140330
// HighScoresService
// Class to hold the list of high scores and to read/write them to a file
//
// Primary author(s): Steven
public class HighScoresService {
    private static final String fileName = "highScores.txt";
    private Context context;
    private ArrayList<HighScore> highScores;
    private File file;

    public HighScoresService(Context context) {
        this.context = context;
        this.highScores = new ArrayList<HighScore>();
        this.file = new File(context.getFilesDir(), fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }


            String line;
            InputStream inputStream = context.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader questionFile = new BufferedReader(inputStreamReader);

            // Read the file line by line to get scores
            while (questionFile.ready()) {
                line = questionFile.readLine();

                if (line.trim().equals("")) break; // gets rid of empty last line

                String split[] = line.split("\t");
                HighScore score = new HighScore(split[0], Integer.parseInt(split[1].trim()));
                this.highScores.add(score);
            }

            questionFile.close();
            inputStreamReader.close();
            inputStream.close();

            this.sort();

        } catch (IOException e) { }
    }

    // Adds a high score to the list of high scores
    // Preserves the largest 5 scores
    public void addHighScore(HighScore highScore) {
        this.highScores.add(highScore);
        this.sort();
        while(this.highScores.size() > 5) {
            this.highScores.remove(5);
        }
        this.writeToFile();
    }

    // Writes the scores to file, replaces the file completely with each write
    private void writeToFile() {
        String separator = System.getProperty("line.separator");

        // rewrite file completely
        file.delete();
        try {
            file.createNewFile();
            FileOutputStream storageFileOut = context.openFileOutput(fileName, Context.MODE_APPEND);
            OutputStreamWriter storageWriter = new OutputStreamWriter(storageFileOut);
            for(HighScore h: highScores) {
                storageWriter.append(h.toString() + separator);
            }

            storageWriter.flush();
            storageWriter.close();
            storageFileOut.close();
        } catch (IOException e) { }
    }

    public ArrayList<HighScore> getHighScores() {
        return this.highScores;
    }

    // Helper method to sort the list in descending order
    private void sort() {
        Collections.sort(highScores, new Comparator<HighScore>() {
            @Override
            public int compare(HighScore h1, HighScore h2) {
                return (new Integer(h2.getScore())).compareTo(new Integer(h1.getScore()));
            }
        });
    }

    // Checks to see if a score is in fact a high score
    // either there are not 5 high scores yet or score is at least higher than the smallest score
    public boolean isHighScore(int score){
        return highScores.size() < 5 || highScores.get(highScores.size() - 1).getScore() < score;
    }
}
