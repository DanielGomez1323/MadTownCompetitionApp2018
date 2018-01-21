package com.example.pollo.madtowncompetitionapp2018;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

public class UploadData extends AppCompatActivity {
    SQLiteDatabase myDB = null;
    ListView lv;
    Cursor c;
    Button uploadButton;
    Button deleteButton;
    Button scrollUpButton;
    Match match;
    String query;
    String address = "";
    String _id;
    //dataListAdapter listAdapter;

    public class Match {
        String scoutName;
        String teamColor;

        String teamNumber;
        String matchNumber;

        String robotPosition;
        String baseLineCrossed;
        String autoHighCubePlaced;
        String autoLowCubePlaced;
        String highCubesPlaced;
        String lowCubesPlaced;
        String vaultCubesPlaced;
        String climbTime;
        String climbSuccess;
        String robotNotes;

        public void loadDatabase(int id) {
            myDB = openOrCreateDatabase("FRC", MODE_PRIVATE, null);
            Cursor c = myDB.rawQuery("SELECT * FROM PowerUp WHERE _id = " + String.valueOf(id), null);
            c.moveToFirst();
            if (c.getCount() > 0) {
                scoutName = c.getString(c.getColumnIndex("scoutName"));
                teamColor = c.getString(c.getColumnIndex("teamColor"));
                teamNumber = c.getString(c.getColumnIndex("teamNumber"));
                matchNumber = c.getString(c.getColumnIndex("matchNumber"));
                robotPosition = c.getString(c.getColumnIndex("robotPosition"));
                baseLineCrossed = c.getString(c.getColumnIndex("baseLineCrossed"));
                autoHighCubePlaced = c.getString(c.getColumnIndex("autoHighCubePlaced"));
                autoLowCubePlaced = c.getString(c.getColumnIndex("autoLowCubePlaced"));
                highCubesPlaced = c.getString(c.getColumnIndex("highCubesPlaced"));
                lowCubesPlaced = c.getString(c.getColumnIndex("lowCubesPlaced"));
                vaultCubesPlaced = c.getString(c.getColumnIndex("vaultCubesPlaced"));
                climbTime = c.getString(c.getColumnIndex("climbTime"));
                climbSuccess = c.getString(c.getColumnIndex("climbSuccess"));
                robotNotes = c.getString(c.getColumnIndex("robotNotes"));
                Toast.makeText(getApplicationContext(), "Team " + teamNumber + ", Match " + matchNumber + " selected", Toast.LENGTH_SHORT).show();
            }
        }

        public boolean isLoaded() {
            boolean hi = false;
            if (teamNumber != null) {
                hi = true;
            } else {
                hi = false;
            }
            return hi;
        }
    }
        public static String toJSon(Match data) {
            try {
                JSONObject object = new JSONObject();
                object.put("scoutName", data.scoutName);
                object.put("teamColor", data.teamColor);
                object.put("teamNumber", data.teamNumber);
                object.put("matchNumber", data.matchNumber);
                object.put("robotPosition", data.robotPosition);
                object.put("baseLineCrossed", data.baseLineCrossed);
                object.put("autoHighCubePlaced", data.autoHighCubePlaced);
                object.put("autoLowCubePlaced", data.autoLowCubePlaced);
                object.put("highCubesPlaced", data.highCubesPlaced);
                object.put("lowCubesPlaced", data.lowCubesPlaced);
                object.put("vaultCubesPlaced", data.vaultCubesPlaced);
                object.put("climbTime", data.climbTime);
                object.put("climbSuccess", data.climbSuccess);
                object.put("robotNotes", data.robotNotes);
                return object.toString();
            } catch (Exception e) {
                System.out.print(e);
            }
            return null;
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_data);
        lv = findViewById(R.id.webListView);
        uploadButton = findViewById(R.id.sendToWebButton);
        deleteButton = findViewById(R.id.deleteButton);
        scrollUpButton = findViewById(R.id.ToTopButton);
        match = new Match();
        query = "SELECT * FROM PowerUp";
        Intent i = getIntent();
        String search = i.getStringExtra("search");
    }
}
