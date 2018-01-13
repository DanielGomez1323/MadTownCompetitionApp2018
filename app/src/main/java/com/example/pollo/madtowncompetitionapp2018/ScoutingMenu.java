package com.example.pollo.madtowncompetitionapp2018;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class ScoutingMenu extends AppCompatActivity {
    AutoCompleteTextView teamNumberAutoEditText;
    EditText matchNumberEditText;
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scouting_menu);
        teamNumberAutoEditText = findViewById(R.id.teamNumberAutoEditText);
        matchNumberEditText = findViewById(R.id.matchNumberEditText);
        startButton = findViewById(R.id.startButton);


    }
}
