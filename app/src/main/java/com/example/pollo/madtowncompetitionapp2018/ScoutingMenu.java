package com.example.pollo.madtowncompetitionapp2018;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ScoutingMenu extends AppCompatActivity {
    EditText scoutNameEditText;
    AutoCompleteTextView teamNumberAutoEditText;
    EditText matchNumberEditText;
    CheckBox blueCheckBox;
    CheckBox redCheckBox;
    Button startButton;

    SQLiteDatabase myDB = null;
    Cursor c;
    String[] teamNumbers = new String[3];
    int i = 0;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scouting_menu);
        scoutNameEditText = findViewById(R.id.scoutNameEditText);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.select_dialog_item, teamNumbers);
        teamNumberAutoEditText = findViewById(R.id.teamNumberAutoEditText);
        teamNumberAutoEditText.setThreshold(1);//will start working from first character
        teamNumberAutoEditText.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        matchNumberEditText = findViewById(R.id.matchNumberEditText);
        blueCheckBox = findViewById(R.id.blueCheckBox);
        redCheckBox = findViewById(R.id.redCheckBox);
        startButton = findViewById(R.id.startButton);

        myDB = openOrCreateDatabase("FRC", MODE_PRIVATE, null);
        c = myDB.rawQuery("SELECT * FROM PowerUp WHERE scoutName = "  + scoutNameEditText.getText(), null);
        c.moveToFirst();
        if (i > 0) {
            scoutNameEditText.setText(c.getString(c.getColumnIndex("scoutName")));
        }else {
            scoutNameEditText.setText("");
        }
        myDB = openOrCreateDatabase("FRC", MODE_PRIVATE, null);
        c = myDB.rawQuery("SELECT * FROM PowerUp ORDER BY _id DESC LIMIT 1", null);
        if (c.getCount()> 0){
            c.moveToFirst();
            int matchNumber = Integer.parseInt(c.getString(c.getColumnIndex("matchNumber"))) + 1;
            matchNumberEditText.setText(String.valueOf(matchNumber));
        }else{
            matchNumberEditText.setText("1");
        }
        myDB = openOrCreateDatabase("FRC", MODE_PRIVATE, null);
        c = myDB.rawQuery("SELECT * FROM MatchSchedule WHERE matchNumber = " + matchNumberEditText.getText(), null);
        if (c.getCount()> 0) {
            c.moveToFirst();
            for(int i = 0 ; i < 3 ; i++) {
                String redTeamNumber = c.getString(c.getColumnIndex("redTeamNumber"));
                teamNumbers[i] = redTeamNumber;
                c.moveToNext();
            }
        }else {
            Toast.makeText(getApplicationContext(), "Did you download the match schedule, idiot?", Toast.LENGTH_LONG).show();
        }
        redCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(blueCheckBox.isChecked()){
                    blueCheckBox.toggle();
                }
            }
        });
        blueCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(redCheckBox.isChecked()){
                    redCheckBox.toggle();
                }
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scoutNameEditText.getText().toString().length() > 0) {
                    if(teamNumberAutoEditText.getText().toString().length() <=4/*teamNumberEditText.getText().toString().length() <=4*/){
                        if(matchNumberEditText.getText().toString().length() > 0) {
                            if ((redCheckBox.isChecked() || blueCheckBox.isChecked()) && !(redCheckBox.isChecked() && blueCheckBox.isChecked())) {
                                Intent autoIntent = new Intent(getApplicationContext(), TabbedScouting.class);
                                autoIntent.putExtra("scoutName", scoutNameEditText.getText().toString());
                                autoIntent.putExtra("teamNumber", teamNumberAutoEditText.getText().toString()/*teamNumberEditText.getText().toString()*/);
                                autoIntent.putExtra("matchNumber", matchNumberEditText.getText().toString());
                                if (redCheckBox.isChecked()) {
                                    autoIntent.putExtra("teamColor", "0");
                                } else {
                                    autoIntent.putExtra("teamColor", "1");
                                }
                                startActivity(autoIntent);
                            }else {
                                Toast.makeText(getApplicationContext(), "Come on dude. Choose a color.", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"Enter a match number hombre.", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Enter a valid team number por favor", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Bro, don't forget your name.", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tabbed_scouting, menu);
        return true;
    }
}
