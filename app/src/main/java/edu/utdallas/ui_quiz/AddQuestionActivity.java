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

public class AddQuestionActivity extends AppCompatActivity {
    QuestionsController controller;
    final int SUCCESS = 1;
    final int FAILURE = 0;
    final int QUIT = -1;

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
                        if (controller.addQuestion(questionText.getText().toString().trim(), trueBtn.isPressed() ? "t" : "f"))
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

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        setResult(QUIT, i);
        finish();
    }
}
