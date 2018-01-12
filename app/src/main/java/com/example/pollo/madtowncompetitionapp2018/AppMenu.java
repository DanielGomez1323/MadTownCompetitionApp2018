package com.example.pollo.madtowncompetitionapp2018;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        //createPicturesDatabase();
        //createDatabase();
        //createMatchDatabase();



    }
}
