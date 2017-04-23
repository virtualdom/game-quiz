package edu.utdallas.ui_quiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {
    int QUIZ = 0;
    int ADD = 1;
    int QUIT = -1;
    int SUCCESS = 1;
    int FAILURE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        final Button quizBtn = (Button) findViewById(R.id.quizBtn);
        final Button addQBtn = (Button) findViewById(R.id.addQuestionBtn);

        final MainMenu self = this;

        quizBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent quizIntent = new Intent(self, QuizQuestionActivity.class);
                startActivityForResult(quizIntent, QUIZ);

            }
        });

        addQBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addQIntent = new Intent(self, AddQuestionActivity.class);
                startActivityForResult(addQIntent, ADD);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == QUIZ) {
            if (resultCode == QUIT) {
                Context context = getApplicationContext();
                CharSequence text = "QUIZ QUIT";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else {
                int score = resultCode;


                // DO THINGS WITH THE SCORE HERE!


                Context context = getApplicationContext();
                CharSequence text = "YOUR SCORE: " + score;
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        } else if (requestCode == ADD) {
            if (resultCode == QUIT) {
                Context context = getApplicationContext();
                CharSequence text = "Question discarded";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else if (resultCode == SUCCESS){
                Context context = getApplicationContext();
                CharSequence text = "Question added!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else {
                Context context = getApplicationContext();
                CharSequence text = "Oops! Failed to add your question!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }
}
