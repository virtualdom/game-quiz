package edu.utdallas.ui_quiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

// Dominic Joseph - dxj120030
// Steven Hogue - sdh140330
// Main Menu Activity
// On this screen, users can
// choose to begin the quiz,
// add their own question, or
// view high scores
//
// Primary author(s): Dominic and Steven
public class MainMenu extends AppCompatActivity {
    int QUIZ = 0;
    int ADD = 1;
    int HIGH = 2;
    int QUIT = -1;
    int SUCCESS = 1;

    MainMenu self;

    // On Main Menu Creation
    // Set Click listeners for each of the
    // menu optoins
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        getSupportActionBar().hide();

        final Button quizBtn = (Button) findViewById(R.id.quizBtn);
        final Button addQBtn = (Button) findViewById(R.id.addQuestionBtn);
        final Button highBtn = (Button) findViewById(R.id.highScoresBtn);

        this.self = this;

        // Start the quiz
        quizBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent quizIntent = new Intent(self, QuizQuestionActivity.class);
                startActivityForResult(quizIntent, QUIZ);
            }
        });

        // Begin the "Add Question" activity
        addQBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addQIntent = new Intent(self, AddQuestionActivity.class);
                startActivityForResult(addQIntent, ADD);

            }
        });

        // Open the high scores screen
        highBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent highScoreIntent = new Intent(self, HighScoresActivity.class);
                startActivity(highScoreIntent);

            }
        });
    }

    // Activity result listener
    // Listens for the completion of any of the
    // activities.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == QUIZ) {
            // If the user quit the quiz,
            // do nothing. If their score
            // was a high score, jump to the
            // New High Score screen. Otherwise,
            // just jump to the View High Scores
            // screen.
            if (resultCode == QUIT) {
                Context context = getApplicationContext();
                CharSequence text = "Quiz progress discarded.";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else {
                if((new HighScoresService(this).isHighScore(resultCode))) {
                    Intent addHighIntent = new Intent(self, NewHighScoreActivity.class);
                    addHighIntent.putExtra("score", String.valueOf(resultCode));
                    startActivityForResult(addHighIntent, HIGH);
                } else {
                    startActivity(new Intent(self, HighScoresActivity.class));
                }
            }
        } else if (requestCode == ADD) {
            // If the user quit adding a question,
            // do nothing. Otherwise, notify them
            // of whether or not the question was
            // successfully added.
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
        } else if (requestCode == HIGH) {
            // If the user just added their high score,
            // go immediately to the high score screen.
            Intent highScoreIntent = new Intent(self, HighScoresActivity.class);
            startActivity(highScoreIntent);
        }
    }
}
