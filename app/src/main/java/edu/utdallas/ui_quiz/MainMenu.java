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
    int QUIZ_QUIT = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        final Button quizBtn = (Button) findViewById(R.id.quizBtn);

        final MainMenu self = this;

        quizBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent quizIntent = new Intent(self, QuizQuestionActivity.class);
                startActivityForResult(quizIntent, QUIZ);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == QUIZ) {
            if (resultCode == QUIZ_QUIT) {
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
        }
    }
}
