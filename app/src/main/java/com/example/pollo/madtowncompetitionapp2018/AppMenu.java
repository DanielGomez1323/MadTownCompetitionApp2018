package com.example.pollo.madtowncompetitionapp2018;

import android.content.ContentValues;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppMenu extends AppCompatActivity {
    EditText searchEditText;
    Button searchButton;
    Button scoutButton;
    Button uploadButton;
    Button teamsButton;
    Button addPhotoButton;
    Button viewPhotoButton;
    Button importScheduleButton;
    Button importDataButton;
    Button threevthreeButton;
    SQLiteDatabase myDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_menu);
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        scoutButton = findViewById(R.id.scoutButton);
        uploadButton = findViewById(R.id.uploadButton);
        teamsButton = findViewById(R.id.teamsButton);
        addPhotoButton = findViewById(R.id.addPhotoButton);
        viewPhotoButton = findViewById(R.id.viewPhotoButton);
        importScheduleButton = findViewById(R.id.importScheduleButton);
        importDataButton = findViewById(R.id.importDataButton);
        threevthreeButton = findViewById(R.id.threevthreeButton);

        createPicturesDatabase();
        createDatabase();
        createMatchDatabase();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(getApplicationContext(), UploadData.class);
                searchIntent.putExtra("search", searchEditText.getText());
                startActivity(searchIntent);
            }
        });
        scoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoutIntent = new Intent(getApplicationContext(), ScouterInfo.class);
                startActivity(scoutIntent);
            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent uploadIntent = new Intent(getApplicationContext(), UploadData.class);
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
                Intent addPhotoIntent = new Intent(getApplicationContext(), AddPhoto.class);
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
        });
        importDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pullData();
            }
        });
    }
    public void createDatabase(){
        try {
            myDB = openOrCreateDatabase("FRC", MODE_PRIVATE, null);
            myDB.execSQL("CREATE TABLE IF NOT EXISTS PowerUp ( _id INTEGER PRIMARY KEY AUTOINCREMENT,/*scoutName varchar, teamColor int,*/ teamNumber int, matchNumber int, robotPosition int, baseLineCrossed int, autoHighCubePlaced int, autoLowCubePlaced int, highCubesPlaced int, lowCubesPlaced int, vaultCubesPlaced int, climbTime int, climbSuccess int, robotNotes varchar)");
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
            myDB.execSQL("CREATE TABLE IF NOT EXISTS MatchSchedule (_id INTEGER PRIMARY KEY AUTOINCREMENT, matchNumber int, redTeamNumber int, blueTeamNumber int)");
            if (myDB != null)
                myDB.close();
        }   catch (SQLException e) {
            Log.e("Error", "Error", e);
        }
    }
    public void pullSchedule(){
        String url = "http://scout.team1323.com/api/v2018/fetchSchedule.php";
        JsonArrayRequest jsArRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray matches) {
                        try {
                            for (int i = 0; i < matches.length(); i++) {
                                JSONObject m = matches.getJSONObject(i);
                                int matchNumber = m.getInt("matchNumber");
                                JSONArray red = m.getJSONArray("red");
                                JSONArray blue = m.getJSONArray("blue");
                                for (int j = 0; j < red.length(); j++) {
                                    int redTeamNumber = red.getInt(j);
                                    for (int k = 0; k < blue.length(); k++) {
                                        int blueTeamNumber = blue.getInt(k);


                                        ContentValues c = new ContentValues();
                                        c.put("matchNumber", matchNumber);
                                        c.put("redTeamNumber", redTeamNumber);
                                        c.put("blueTeamNumber", blueTeamNumber);
                                        myDB = openOrCreateDatabase("FRC", MODE_PRIVATE, null);
                                        try {
                                            myDB.insertOrThrow("MatchSchedule", null, c);
                                        } catch (SQLException s) {
                                            Toast.makeText(getApplication(), "Error saving", Toast.LENGTH_SHORT).show();
                                        }
                                        if (myDB != null) {
                                            myDB.close();
                                        }
                                    }
                                }

                            }
                        } catch (final JSONException e) {
                            Log.d("ERROR", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        error.printStackTrace();
                    }
                });
        Volley.newRequestQueue(this).add(jsArRequest);
    }
    public void pullData(){
        String url = "http://scout.team1323.com/api/v2018/fetchData.php";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray match = response.getJSONArray("match");
                            for (int i = 0; i < match.length(); i++) {
                                JSONObject m = match.getJSONObject(i);
                                int teamNumber = m.getInt("teamNumber");
                                int matchNumber = m.getInt("matchNumber");
                                int robotPosition = m.getInt("robotPosition");
                                int baseLineCrossed = m.getInt("baseLineCrossed");
                                int autoHighCubePlaced = m.getInt("autoHighCubePlaced");
                                int autoLowCubePlaced = m.getInt("autoLowCubePlaced");
                                int highCubesPlaced = m.getInt("highCubesPlaced");
                                int lowCubesPlaced = m.getInt("lowCubesPlaced");
                                int vaultCubesPlaced = m.getInt("vaultCubesPlaced");
                                int climbTime = m.getInt("climbTime");
                                int climbSuccess = m.getInt("climbSuccess");

                                ContentValues c = new ContentValues();
                                c.put("teamNumber", teamNumber);
                                c.put("matchNumber", matchNumber);
                                c.put("robotPosition", robotPosition);
                                c.put("baseLineCrossed", baseLineCrossed);
                                c.put("autoHighCubePlaced", autoHighCubePlaced);
                                c.put("autoLowCubePlaced", autoLowCubePlaced);
                                c.put("highCubesPlaced", highCubesPlaced);
                                c.put("lowCubesPlaced", lowCubesPlaced);
                                c.put("vaultCubesPlaced", vaultCubesPlaced);
                                c.put("climbTime", climbTime);
                                c.put("climbSuccess", climbSuccess);
                                myDB = openOrCreateDatabase("FRC", MODE_PRIVATE, null);
                                try {
                                    myDB.insertOrThrow("PowerUp", null, c);
                                }catch (SQLException s){
                                        Toast.makeText(getApplication(), "Ooh, you almost had it.", Toast.LENGTH_SHORT).show();
                                    }
                                    if (myDB != null){
                                        myDB.close();
                                }
                            }
                        }catch (final JSONException e) {
                            Log.d("ERROR", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        error.printStackTrace();
                    }
                });
        Volley.newRequestQueue(this).add(jsObjRequest);
    }
}
