package edu.utdallas.ui_quiz;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HighScoresActivity extends AppCompatActivity {
    private HighScoresService highScoresService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        this.highScoresService = new HighScoresService(this);
        ArrayList<HighScore> highScores = this.highScoresService.getHighScores();
        TableLayout tableLayout = (TableLayout) findViewById(R.id.highScoresTable);

        if(!highScores.isEmpty()) {
            for (HighScore h : highScores) {
                TableRow row = (TableRow) getLayoutInflater().inflate(R.layout.tablerow_high_score, null);
                ((TextView) row.findViewById(R.id.nameRow)).setText(h.getName());
                ((TextView) row.findViewById(R.id.scoreRow)).setText(h.getScore());
                tableLayout.addView(row);
            }
        } else {
            TableRow row = (TableRow) this.getLayoutInflater().inflate(R.layout.tablerow_no_high_scores, null);
            row.addView(row);
        }

        Button backBtn = (Button) findViewById(R.id.backToMenuBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
