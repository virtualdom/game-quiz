package edu.utdallas.ui_quiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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

        Intent i = this.getIntent();
        this.scoreText.setText(i.getStringExtra("score"));
    }

    public void addHighScore() {
        if(this.nameCtl.getText().length() < 3) {
            this.nameCtl.setError("Enter at least 3 characters");
            return;
        } else {
            this.highScoresService.addHighScore(new HighScore(this.nameCtl.getText().toString(),
                    Integer.parseInt(this.scoreText.getText().toString())));
            this.finish();
        }
    }
}
