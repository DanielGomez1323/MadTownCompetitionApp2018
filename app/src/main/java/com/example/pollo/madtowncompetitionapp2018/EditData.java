package com.example.pollo.madtowncompetitionapp2018;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditData extends AppCompatActivity {
    EditText teamNumberEnterText;
    EditText matchNumberEnterText;
    //EditText teamColorEnterText;
    EditText robotPositionEnterText;
    EditText baseLineEnterText;
    EditText autoHighCubeEnterText;
    EditText autoLowCubeEnterText;
    EditText highCubesPlacedEnterText;
    EditText lowCubesPlacedEnterText;
    EditText vaultCubesPlacedEnterText;
    EditText climbTimeEnterText;
    EditText climbSuccessEnterText;
    EditText notesEnterText;

    Button confirmEditsButton;

    String tNumber;
    String mNumber;

    SQLiteDatabase myDB = null;
    Cursor c;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        teamNumberEnterText = findViewById(R.id.teamNumberEnterText);
        matchNumberEnterText = findViewById(R.id.matchNumberEnterText);
        //teamColorEnterText = findViewById(R.id.teamColorEnterText);
        robotPositionEnterText = findViewById(R.id.robotPositionEnterText);
        baseLineEnterText = findViewById(R.id.baseLineEnterText);
        autoHighCubeEnterText = findViewById(R.id.autoHighCubeEnterText);
        autoLowCubeEnterText = findViewById(R.id.autoLowCubeEnterText);
        highCubesPlacedEnterText = findViewById(R.id.highCubesPlacedEnterText);
        lowCubesPlacedEnterText = findViewById(R.id.lowCubesPlacedEnterText);
        vaultCubesPlacedEnterText = findViewById(R.id.vaultCubesPlacedEnterText);
        climbTimeEnterText = findViewById(R.id.climbTimeEnterText);
        climbSuccessEnterText = findViewById(R.id.climbSuccessEnterText);
        notesEnterText = findViewById(R.id.notesEnterText);
        confirmEditsButton = findViewById(R.id.confirmEditsButton);

        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        myDB = openOrCreateDatabase("FRC", MODE_PRIVATE, null);
        c = myDB.rawQuery("SELECT * FROM PowerUp WHERE _id =" + id, null);
        c.moveToFirst();
        int i = c.getCount();
        if (i > 0) {
            teamNumberEnterText.setText(c.getString(c.getColumnIndex("teamNumber")), TextView.BufferType.EDITABLE);
            matchNumberEnterText.setText(c.getString(c.getColumnIndex("matchNumber")), TextView.BufferType.EDITABLE);
            //teamColorEnterText.setText(c.getString(c.getColumnIndex("teamColor")), TextView.BufferType.EDITABLE);
            robotPositionEnterText.setText(c.getString(c.getColumnIndex("robotPosition")), TextView.BufferType.EDITABLE);
            baseLineEnterText.setText(c.getString(c.getColumnIndex("baseLineCrossed")), TextView.BufferType.EDITABLE);
            autoHighCubeEnterText.setText(c.getString(c.getColumnIndex("autoHighCubePlaced")), TextView.BufferType.EDITABLE);
            autoLowCubeEnterText.setText(c.getString(c.getColumnIndex("autoLowCubePlaced")), TextView.BufferType.EDITABLE);
            highCubesPlacedEnterText.setText(c.getString(c.getColumnIndex("highCubesPlaced")), TextView.BufferType.EDITABLE);
            lowCubesPlacedEnterText.setText(c.getString(c.getColumnIndex("lowCubesPlaced")), TextView.BufferType.EDITABLE);
            vaultCubesPlacedEnterText.setText(c.getString(c.getColumnIndex("vaultCubesPlaced")), TextView.BufferType.EDITABLE);
            climbTimeEnterText.setText(c.getString(c.getColumnIndex("climbTime")), TextView.BufferType.EDITABLE);
            climbSuccessEnterText.setText(c.getString(c.getColumnIndex("climbSuccess")), TextView.BufferType.EDITABLE);
            notesEnterText.setText(c.getString(c.getColumnIndex("robotNotes")), TextView.BufferType.EDITABLE);
        }
        c.close();
        myDB.close();
        confirmEditsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeData();
            }
        });
    }
    public void changeData(){
        try{
            myDB = openOrCreateDatabase("FRC", MODE_PRIVATE, null);
            String robotNotes = notesEnterText.getText().toString();
            robotNotes = robotNotes.replace("'","*");
            myDB.execSQL("UPDATE PowerUp SET teamNumber = " + teamNumberEnterText.getText().toString() +
                    ", matchNumber = " + matchNumberEnterText.getText().toString() + /*", teamColor = " +
                    teamColorEnterText.getText().toString() +*/ ", robotPosition = " + robotPositionEnterText.getText().toString() +
                    ", baseLineCrossed = " + baseLineEnterText.getText().toString() + ", autoHighCubePlaced = " +
                    autoHighCubeEnterText.getText().toString() + ", autoLowCubePlaced = " + autoLowCubeEnterText.getText().toString() +
                    ", highCubesPlaced = " + highCubesPlacedEnterText.getText().toString() + ", lowCubesPlaced = " +
                    lowCubesPlacedEnterText.getText().toString() + ", vaultCubesPlaced = " + vaultCubesPlacedEnterText.getText().toString() +
                    ", climbTime = " + climbTimeEnterText.getText().toString() + ", climbSuccess = " +
                    climbSuccessEnterText.getText().toString() + ", robotNotes = '" + robotNotes + "' WHERE _id = " + id);
        }catch (SQLException e){
            System.out.print(e);
        }
        Cursor cur = myDB.rawQuery("SELECT * FROM PowerUp Where _id =" + id, null);
        cur.moveToFirst();
        tNumber = cur.getString(cur.getColumnIndex("teamNumber"));
        mNumber = cur.getString(cur.getColumnIndex("matchNumber"));
        cur.close();
        myDB.close();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("TEAM_NUMBER", tNumber);
        returnIntent.putExtra("MATCH_NUMBER",  mNumber);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
