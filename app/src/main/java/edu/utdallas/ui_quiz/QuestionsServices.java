package edu.utdallas.ui_quiz;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class QuestionsServices {
    Resources resources;
    Context context;
    String storageFilename = "questionBank.txt";

    public QuestionsServices (Resources resources, Context context) {
        this.resources = resources;
        this.context = context;

        File f = context.getFileStreamPath(storageFilename);
        if (f == null || !f.isFile()) {
            try {
                f.createNewFile();

                InputStream rawResource = resources.openRawResource(R.raw.questions);
                ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
                int size = 0;

                byte[] buffer = new byte[1024];
                while((size=rawResource.read(buffer,0,1024))>=0){
                    outputStream.write(buffer,0,size);
                }
                rawResource.close();
                buffer=outputStream.toByteArray();

                FileOutputStream storageFileOut = context.openFileOutput(storageFilename, Context.MODE_APPEND);
                storageFileOut.write(buffer);
                storageFileOut.close();
            }
            catch (Exception e) { Log.e("FILE OPEN ERR", "couldn't create a new storage file"); }
        }
    }

    public void addQuestion (Question question) throws Exception {
        String separator = System.getProperty("line.separator");
        FileOutputStream storageFileOut = context.openFileOutput(storageFilename, Context.MODE_APPEND);
        OutputStreamWriter storageWriter = new OutputStreamWriter(storageFileOut);
        storageWriter.append(separator + question.toString());
        storageWriter.flush();
        storageWriter.close();
        storageFileOut.close();
    }

    public ArrayList<Question> getQuestions () throws IOException {
        ArrayList<Question> questions = new ArrayList<Question>();
        String questionLine;
        String [] questionParts;

        InputStream inputStream = context.openFileInput(storageFilename);
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
