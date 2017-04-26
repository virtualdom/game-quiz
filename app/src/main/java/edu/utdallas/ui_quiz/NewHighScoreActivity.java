package edu.utdallas.ui_quiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

// Dominic Joseph - dxj120030
// Steven Hogue - sdh140330
// NewHighScoreActivity
// Class to allow user to add a new high score to the list
//
// Primary author(s): Steven
public class NewHighScoreActivity extends AppCompatActivity {
    private HighScoresService highScoresService;
    private Button submitBtn;
    private EditText nameCtl;
    private TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_high_score);
        getSupportActionBar().hide();

        this.highScoresService = new HighScoresService(this);

        this.submitBtn = (Button) findViewById(R.id.submitBtn);
        this.submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addHighScore();
            }
        });
        this.nameCtl = (EditText) findViewById(R.id.nameCtl);
        this.scoreText = (TextView) findViewById(R.id.scoreText);
        // make sure the text is no more than 3 characters
        this.scoreText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) });

        Intent i = this.getIntent();
        this.scoreText.setText(i.getStringExtra("score"));
    }

    // Gets name and score and inserts them into the high score list
    public void addHighScore() {
        if(this.nameCtl.getText().length() < 3) {
            this.nameCtl.setError("Name must be 3 characters");
            return;
        } else {
            this.highScoresService.addHighScore(new HighScore(this.nameCtl.getText().toString(),
                    Integer.parseInt(this.scoreText.getText().toString())));
            this.finish();
        }
    }
}
