package com.example.pollo.madtowncompetitionapp2018;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class AppMenu extends AppCompatActivity {
    EditText searchEditText;
    Button searchButton;
    Button scoutButton;
    Button uploadButton;
    Button teamsButton;
    Button addPhotoButton;
    Button viewPhotoButton;
    Button importScheduleButton;
    SQLiteDatabase myDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_menu);
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        scoutButton = findViewById(R.id.scoutButton);
        uploadButton = findViewById(R.id.uploadButton);
        importScheduleButton = findViewById(R.id.importScheduleButton);

        createPicturesDatabase();
        createDatabase();
        createMatchDatabase();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        /*searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(getApplicationContext(), DataUpload.class);
                searchIntent.putExtra("search", searchMenuSearchBox.getText());
                startActivity(searchIntent);
            }
        });*/
        scoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoutIntent = new Intent(getApplicationContext(), ScouterInfo.class);
                startActivity(scoutIntent);
            }
        });
        /*uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent uploadIntent = new Intent(getApplicationContext(), DataUpload.class);
                startActivity(uploadIntent);
            }
        });
        teamsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent teamsIntent = new Intent(getApplicationContext(), TeamRoster.class);
                startActivity(teamsIntent);
            }
        });
        addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPhotoIntent = new Intent(getApplicationContext(), addPhoto.class);
                startActivity(addPhotoIntent);
            }
        });
        viewPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewPhotoIntent = new Intent(getApplicationContext(), TeamPictureSelection.class);
                startActivity(viewPhotoIntent);
            }
        });
        importScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pullSchedule();
            }
        });*/
    }
    public void createDatabase(){
        try {
            myDB = openOrCreateDatabase("FRC", MODE_PRIVATE, null);
            myDB.execSQL("CREATE TABLE IF NOT EXISTS PowerUp ( _id INTEGER PRIMARY KEY AUTOINCREMENT, scoutName varchar, allianceColor int, teamNumber int, matchNumber int, robotPosition int, baseLineCrossed int, autoHighCubePlaced int, autoLowCubePlaced int, highCubesPlaced int, lowCubesPlaced int, vaultCubesPlaced int, climbTime int, climbSuccess int, robotNotes varchar)");
            if (myDB != null)
                myDB.close();
        }catch (SQLException e) {
            Log.e("Error", "Error", e);
        }
    }
    public void createPicturesDatabase(){
        try{
            myDB = openOrCreateDatabase("FRC", MODE_PRIVATE, null);
            myDB.execSQL("CREATE TABLE IF NOT EXISTS TeamPictures ( _id INTEGER PRIMARY KEY AUTOINCREMENT, teamNumber int, pic1 VARCHAR)");
            if (myDB != null)
                myDB.close();
        } catch (SQLException e) {
            Log.e("Error", "Error", e);
        }
    }
    public void createMatchDatabase(){
        try{
            myDB = openOrCreateDatabase("FRC", MODE_PRIVATE, null);
            myDB.execSQL("CREATE TABLE IF NOT EXISTS MatchSchedule (_id INTEGER PRIMARY KEY AUTOINCREMENT, matchNumber int, teamNumber int, teamColor int)");
            if (myDB != null)
                myDB.close();
        }   catch (SQLException e) {
            Log.e("Error", "Error", e);
        }
    }

}
