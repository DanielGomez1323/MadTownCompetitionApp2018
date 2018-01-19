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
import android.widget.EditText;
import android.widget.Toast;

public class ScoutingMenu extends AppCompatActivity {
    AutoCompleteTextView teamNumberAutoEditText;
    EditText matchNumberEditText;
    Button startButton;

    SQLiteDatabase myDB = null;
    Cursor c;
    String[] teamNumbers = new String[3];
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scouting_menu);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.select_dialog_item, teamNumbers);
        teamNumberAutoEditText = findViewById(R.id.teamNumberAutoEditText);
        teamNumberAutoEditText.setThreshold(1);//will start working from first character
        teamNumberAutoEditText.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        matchNumberEditText = findViewById(R.id.matchNumberEditText);
        startButton = findViewById(R.id.startButton);

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
                String teamNumber = c.getString(c.getColumnIndex("teamNumber"));
                teamNumbers[i] = teamNumber;
                c.moveToNext();
            }
        }else {
            Toast.makeText(getApplicationContext(), "Did you download the match schedule, idiot?", Toast.LENGTH_LONG).show();
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(teamNumberAutoEditText.getText().toString().length() <=4/*teamNumberEditText.getText().toString().length() <=4*/){
                    if(matchNumberEditText.getText().toString().length() > 0){
                            Intent autoIntent = new Intent(getApplicationContext(), TabbedScouting.class);
                            autoIntent.putExtra("teamNumber", teamNumberAutoEditText.getText().toString()/*teamNumberEditText.getText().toString()*/);
                            autoIntent.putExtra("matchNumber", matchNumberEditText.getText().toString());
                            startActivity(autoIntent);
                    }else{
                        Toast.makeText(getApplicationContext(),"Enter a match number hombre.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Enter a valid team number por favor", Toast.LENGTH_SHORT).show();
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
