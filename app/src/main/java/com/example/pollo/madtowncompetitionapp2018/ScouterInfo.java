package com.example.pollo.madtowncompetitionapp2018;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;

public class ScouterInfo extends AppCompatActivity {
    EditText scoutNameEdtiText;
    CheckBox blueCheckBox;
    CheckBox redCheckBox;
    Button scoutMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scouter_info);
        scoutNameEdtiText = findViewById(R.id.scoutNameEditText);
        blueCheckBox = findViewById(R.id.blueCheckBox);
        redCheckBox = findViewById(R.id.redCheckBox);
        scoutMenuButton = findViewById(R.id.scoutMenuButton);

        scoutMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scoutMenuIntent = new Intent(getApplicationContext(), ScoutingMenu.class);
                startActivity(scoutMenuIntent);
            }
        });
    }
}
