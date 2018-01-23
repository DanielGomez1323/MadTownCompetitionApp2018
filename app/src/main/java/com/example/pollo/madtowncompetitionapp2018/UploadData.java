package com.example.pollo.madtowncompetitionapp2018;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class UploadData extends AppCompatActivity {
    SQLiteDatabase myDB = null;
    ListView lv;
    Cursor c;
    Button uploadButton;
    Button deleteButton;
    Button scrollUpButton;
    Match match;
    String query;
    String address = "http://www.gorohi.com/1323/2017/data.php";
    String _id;
    DataListAdapter listAdapter;

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
                //scoutName = c.getString(c.getColumnIndex("scoutName"));
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
                robotNotes = c.getString(c.getColumnIndex("scoutName" + "robotNotes"));
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
                //object.put("scoutName", data.scoutName);
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
                object.put("robotNotes", data.robotNotes + data.scoutName);
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
        if (search != null) {
            if (search.toLowerCase().contains("t")) {
                search = search.replaceAll("[^\\d.]", "");
                query = "SELECT * FROM PowerUp WHERE teamNumber = " + search;
            } else if (search.toLowerCase().contains("m")) {
                search = search.replaceAll("[^\\d.]", "");
                query = "SELECT * FROM PowerUp WHERE matchNumber = " + search;
            } else {
                search = search.replaceAll("[^\\d.]", "");
                query = "SELECT * FROM PowerUp WHERE teamNumber = " + search + " OR matchNumber = " + search;
            }
        }
        myDB = openOrCreateDatabase("FRC", MODE_PRIVATE, null);

        c = myDB.rawQuery(query, null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            try {
                listAdapter = new DataListAdapter(UploadData.this, c, 0);
                lv.setAdapter(listAdapter);
                lv.setLongClickable(true);
                lv.setSelection(listAdapter.getCount() - 1);
                c.moveToPosition(listAdapter.getCount() - 1);
                _id = c.getString(c.getColumnIndex("_id"));
                match.loadDatabase(Integer.parseInt(_id));
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Cursor cc = (Cursor) listAdapter.getItem(position);
                        cc.moveToPosition(position);
                        _id = cc.getString(cc.getColumnIndex("_id"));
                        match.loadDatabase(Integer.parseInt(_id));
                    }
                });
                lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                                   int pos, long id) {
                        // TODO Auto-generated method stub
                        Cursor cur = (Cursor) listAdapter.getItem(pos);
                        cur.moveToPosition(pos);
                        String editID = cur.getString(cur.getColumnIndex("_id"));
                        Intent editIntent = new Intent(getApplicationContext(), EditData.class);
                        editIntent.putExtra("ID", editID);
                        startActivityForResult(editIntent, 1);
                        return true;
                    }
                });
            } catch (Exception e) {
                Log.d("ERROR", e.toString());
            }


            uploadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (match.isLoaded()) {
                        String json = toJSon(match);
                        if (json != null) {
                            new MyAsyncTask().execute(json);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Hey, select a match man.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        deleteButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                myDB = openOrCreateDatabase("FRC", MODE_PRIVATE, null);
                myDB.execSQL("DELETE FROM SteamWorks WHERE _id = " + _id);
                updateList();
                return true;
            }
        });
        scrollUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv.smoothScrollToPosition(0);
            }
        });
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {
        String response = "";

        @Override
        protected Double doInBackground(String... params) {
            postData(params[0]);
            return null;
        }

        protected void onPostExecute(Double result) {
            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
        }


        public void postData(String jsonString) {
            HttpsURLConnection httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(address);
            InputStream inputStream = null;
            try {
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
                nameValuePair.add(new BasicNameValuePair("data", jsonString));
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                HttpResponse httpResponse = httpClient.execute(httpPost);
                inputStream = httpResponse.getEntity().getContent();
                if (inputStream != null) {
                    response = convertInputStreamToString(inputStream);
                } else {
                    response = "Did not Work!";
                }
                inputStream.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(Integer.parseInt(android.os.Build.VERSION.SDK)> 5
                && keyCode == KeyEvent.KEYCODE_BACK){
            c.close();
            myDB.close();

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            c.close();
            myDB.close();
            Intent i = new Intent(this, ScoutingMenu.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            return true;
        }
        return super.onKeyLongPress(keyCode, event);
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
        {
            result += line;
        }
        inputStream.close();
        return result;
    }
    public void updateList(){
        myDB = openOrCreateDatabase("FRC", MODE_PRIVATE, null);
        Cursor c2  = myDB.rawQuery(query, null);
        listAdapter.changeCursor(c2);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String match = data.getStringExtra("MATCH_NUMBER");
                String teamN = data.getStringExtra("TEAM_NUMBER");
                Toast.makeText(getApplicationContext(),"Team " + teamN + ", Match " + match + "updated.", Toast.LENGTH_SHORT).show();
                updateList();
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }
}
