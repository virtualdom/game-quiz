package edu.utdallas.ui_quiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

// Dominic Joseph - dxj120030
// Steven Hogue - sdh140330
// Quiz Activity
//
// Primary author(s): Dominic
public class QuizQuestionActivity extends AppCompatActivity {
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private TextView questionText;
    private TextView scoreText;
    private QuestionsController controller;
    private Question currentQuestion;
    Button trueBtn;
    Button falseBtn;

    private static int QUIT = -1;

    // On activity creation
    // Simply get ahold of all onpage
    // components.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new QuestionsController(getResources(), getApplicationContext());
        setContentView(R.layout.activity_quiz_question);

        mContentView = findViewById(R.id.fullscreen_content);
        questionText = (TextView) findViewById(R.id.questionText);
        scoreText = (TextView) findViewById(R.id.scoreText);
    }

    // Begin the quiz
    private void beginQuiz () {
        controller.reset();
        update();
    }

    // Update the current UI
    private void update() {
        currentQuestion = controller.getQuestion();
        scoreText.setText(String.valueOf(controller.getScore()));
        questionText.setText(currentQuestion.getQuestion());
    }

    // Show a dismissable popup that contains
    // additional information about the question.
    //
    // This takes a boolean because, at one point,
    // we were considering showing additional info
    // even if they get the answer correct. If that
    // were the case, we wouldn't want the "Dismiss"
    // button to end the activity. This has been scrapped
    // for now.
    private void showAdditionalInfo (boolean correct) {
        if (!correct) {
            trueBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { return; }
            });

            falseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    return;
                }
            });
            LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = layoutInflater.inflate(R.layout.popup, null);
            final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            popupWindow.showAtLocation(findViewById(R.id.fullscreen_content), Gravity.CENTER_HORIZONTAL,0,0);
            ((TextView)popupWindow.getContentView().findViewById(R.id.popup_text)).setText(currentQuestion.getAdditionalInfo());
            ((Button)popupWindow.getContentView().findViewById(R.id.dismissBtn)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent finishedQuizIntent = new Intent();
                    setResult(controller.getScore(),finishedQuizIntent);
                    finish();
                }
            });

        }
    }

    // submit an answer to the question
    private void answer (boolean answer) {
        boolean correct = controller.answerQuestion(answer);
        showAdditionalInfo(correct);
        if (correct) update();
    }

    // quit the quiz when the back button is pressed
    @Override
    public void onBackPressed() {
        // REPLACE THIS TO SOMEHOW ASK THE
        // USER TO CONFIRM THAT THEY WOULD
        // LIKE TO QUIT THE QUIZ
        Intent i = new Intent();
        setResult(QUIT, i);
        finish();
    }

    // The next three functions were added automatically
    // by Android Studio when we asked it to create a
    // FullScreenActivity.
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    };
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
            mHideHandler.removeCallbacks(mShowPart2Runnable);
            mHideHandler.postDelayed(mHidePart2Runnable, 300);
        }
    };

    // Set callbacks, including True/False button click
    // listeners.
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, 100);

        trueBtn = (Button) findViewById(R.id.trueBtn);
        falseBtn = (Button) findViewById(R.id.falseBtn);

        trueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer(true);
            }
        });
        falseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer(false);
            }
        });

        beginQuiz();
    }
}
