package edu.utdallas.ui_quiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

// Dominic Joseph - dxj120030
// Steven Hogue - sdh140330
// Add Question Activity
// On this screen, users get
// an opportunity to add their
// own True/False question to
// the quiz game.
//
// Primary author(s): Dominic

public class AddQuestionActivity extends AppCompatActivity {
    QuestionsController controller;
    final int SUCCESS = 1;
    final int FAILURE = 0;
    final int QUIT = -1;

    // On Activity creation
    // This method gets a reference to a controller
    // and sets the button press handlers for the
    // three buttons
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        getSupportActionBar().hide();

        controller = new QuestionsController(getResources(), getApplicationContext());

        final TextView questionText = (TextView) findViewById(R.id.addQuestionTxtBox);
        final Button trueBtn = (Button) findViewById(R.id.setTrueBtn);
        final Button falseBtn = (Button) findViewById(R.id.setFalseBtn);
        final Button submitBtn = (Button) findViewById(R.id.submitBtn);

        // if the true or false buttons are pressed,
        // unpress the opposite button and set the
        // pressed one as held down
        trueBtn.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    trueBtn.setPressed(true);
                    falseBtn.setPressed(false);
                }
                return true;
            }
        });

        falseBtn.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    falseBtn.setPressed(true);
                    trueBtn.setPressed(false);
                }
                return true;
            }
        });

        // if all inputs are given, allow
        // the question to be added and
        // complete the activity
        submitBtn.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if (questionText.getText().toString().trim().equals("")) {
                        Context context = getApplicationContext();
                        CharSequence text = "Please type in a question!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else if (!trueBtn.isPressed() && !falseBtn.isPressed()) {
                        Context context = getApplicationContext();
                        CharSequence text = "Please select true or false!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else {
                        Intent finishedAddingIntent = new Intent();
                        if (controller.addQuestion(questionText.getText().toString().replaceAll("\\s", " ").trim(), trueBtn.isPressed() ? "t" : "f"))
                            setResult(SUCCESS, finishedAddingIntent);
                        else
                            setResult(FAILURE, finishedAddingIntent);
                        finish();
                    }
                }
                return true;
            }
        });
    }

    // Back Button Handler
    // If the user presses the back button,
    // finish the activity without saving
    // the question.
    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        setResult(QUIT, i);
        finish();
    }
}
