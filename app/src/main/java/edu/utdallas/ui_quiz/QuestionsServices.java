package edu.utdallas.ui_quiz;

import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class QuestionsServices {
    Resources resources;

    public QuestionsServices (Resources resources) {
        this.resources = resources;
    }

    public ArrayList<Question> getQuestions () throws IOException {
        ArrayList<Question> questions = new ArrayList<Question>();
        String questionLine;
        String [] questionParts;

        InputStream inputStream = resources.openRawResource(R.raw.questions);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader questionFile = new BufferedReader(inputStreamReader);

        while (questionFile.ready()) {
            questionLine = questionFile.readLine();
            questionParts = questionLine.split("\t");
            questions.add(new Question(questionParts[0], questionParts[1], questionParts[2]));
        }

        questionFile.close();
        inputStreamReader.close();
        inputStream.close();

        return questions;
    }
}
