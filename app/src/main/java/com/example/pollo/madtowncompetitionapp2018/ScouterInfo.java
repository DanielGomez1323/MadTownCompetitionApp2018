package com.example.pollo.madtowncompetitionapp2018;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.Toast;

public class ScouterInfo extends AppCompatActivity {
    EditText scoutNameEditText;
    CheckBox blueCheckBox;
    CheckBox redCheckBox;
    Button scoutMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scouter_info);
        scoutNameEditText = findViewById(R.id.scoutNameEditText);
        blueCheckBox = findViewById(R.id.blueCheckBox);
        redCheckBox = findViewById(R.id.redCheckBox);
        scoutMenuButton = findViewById(R.id.scoutMenuButton);

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
        scoutMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scoutNameEditText.getText().toString().length() > 0) {
                    if ((redCheckBox.isChecked() || blueCheckBox.isChecked()) && !(redCheckBox.isChecked() && blueCheckBox.isChecked())) {
                        Intent autoIntent = new Intent(getApplicationContext(), ScoutingMenu.class);
                        autoIntent.putExtra("scoutName", scoutNameEditText.getText().toString()/*Tells the app to take the scouts name and save it*/);
                        if (redCheckBox.isChecked()) {
                            autoIntent.putExtra("allianceColor", "0");
                        } else {
                            autoIntent.putExtra("allianceColor", "1");
                        }
                        startActivity(autoIntent);
                    }else {
                        Toast.makeText(getApplicationContext(), "Come on dude. Choose a color.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Bro, don't forget your name.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
