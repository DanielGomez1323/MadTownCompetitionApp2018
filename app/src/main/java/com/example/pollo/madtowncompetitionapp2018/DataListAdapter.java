package com.example.pollo.madtowncompetitionapp2018;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DataListAdapter extends CursorAdapter{
    private LayoutInflater cursorInflater;
    private Context mContext;

    public DataListAdapter(Context context, Cursor c, int flags){
        super(context, c, flags);
        mContext = context;
    }
    public void bindView(View view, Context context, Cursor cursor){
        TextView scoutTextView = view.findViewById(R.id.scoutTextView);
        TextView teamNumberTextView = view.findViewById(R.id.teamNumberTextView);
        TextView matchNumberTextView = view.findViewById(R.id.matchNumberTextView);
        TextView teamColorTextView = view.findViewById(R.id.teamColorTextView);
        TextView robotPositionTextView = view.findViewById(R.id.robotPositionTextView);
        TextView baseLineCrossedTextView = view.findViewById(R.id.baseLineCrossedTextView);
        TextView autoHighTextView = view.findViewById(R.id.autoHighTextView);
        TextView autoLowTextView = view.findViewById(R.id.autoLowTextView);
        TextView highCubesTextView = view.findViewById(R.id.highCubesTextView);
        TextView lowCubesTextView = view.findViewById(R.id.lowCubesTextView);
        TextView vaultCubesTextView = view.findViewById(R.id.vaultCubesTextView);
        TextView robotClimbTextView = view.findViewById(R.id.robotClimbTextView);
        TextView successTextView = view.findViewById(R.id.successTextView);
        TextView robotNotesTextView = view.findViewById(R.id.robotNotesTextView);
        scoutTextView.setText(scoutTextView.getText() + cursor.getString(cursor.getColumnIndex("scoutName")));
        teamNumberTextView.setText(teamNumberTextView.getText() + cursor.getString(cursor.getColumnIndex("teamNumber")));
        matchNumberTextView.setText(matchNumberTextView.getText() + cursor.getString(cursor.getColumnIndex("matchNumber")));
        teamColorTextView.setText(teamColorTextView.getText() + cursor.getString(cursor.getColumnIndex("teamColor")));
        robotPositionTextView.setText(robotPositionTextView.getText() + cursor.getString(cursor.getColumnIndex("robotPosition")));
        baseLineCrossedTextView.setText(baseLineCrossedTextView.getText() + cursor.getString(cursor.getColumnIndex("baseLineCrossed")));
        autoHighTextView.setText(autoHighTextView.getText() + cursor.getString(cursor.getColumnIndex("autoHighCubePlaced")));
        autoLowTextView.setText(autoLowTextView.getText() + cursor.getString(cursor.getColumnIndex("autoLowCubePlaced")));
        highCubesTextView.setText(highCubesTextView.getText() + cursor.getString(cursor.getColumnIndex("highCubesPlaced")));
        lowCubesTextView.setText(lowCubesTextView.getText() + cursor.getString(cursor.getColumnIndex("lowCubesPlaced")));
        vaultCubesTextView.setText(vaultCubesTextView.getText() + cursor.getString(cursor.getColumnIndex("vaultCubesPlaced")));
        robotClimbTextView.setText(robotClimbTextView.getText() + cursor.getString(cursor.getColumnIndex("climbTime")));
        successTextView.setText(successTextView.getText() + cursor.getString(cursor.getColumnIndex("climbSuccess")));
        robotNotesTextView.setText(robotNotesTextView.getText() + cursor.getString(cursor.getColumnIndex("robotNotes")));
        teamNumberTextView.setText("Team " + cursor.getString(cursor.getColumnIndex("teamNumber")));
        matchNumberTextView.setText("Match " + cursor.getString(cursor.getColumnIndex("matchNumber")));
        String color = cursor.getString(cursor.getColumnIndex("teamColor"));
        switch(cursor.getString(cursor.getColumnIndex("teamColor"))){
            case "1":
                color = "Blue";
                break;
            case "0":
                color = "Red";
                break;
            default:
                break;
        }
        String robotPosition = cursor.getString(cursor.getColumnIndex("robotPosition"));
        switch(cursor.getString(cursor.getColumnIndex("robotPosition"))){
            case "0":
                robotPosition = "Left";
                break;
            case "1":
                robotPosition = "Middle";
                break;
            case "2":
                robotPosition = "Right";
                break;
            case "3":
                robotPosition = "Did Not Show";
            default:
                break;
        }
        String baseLineCrossed = cursor.getString(cursor.getColumnIndex("baseLineCrossed"));
        switch (cursor.getString(cursor.getColumnIndex("baseLineCrossed"))){
            case "0":
                baseLineCrossed = "Baseline Not Crossed";
                break;
            case "1":
                baseLineCrossed = "Baseline Crossed";
                break;
            default:
                break;
        }
        teamColorTextView.setText(color);
        robotPositionTextView.setText(robotPosition);
        baseLineCrossedTextView.setText(baseLineCrossed);
        autoHighTextView.setText("Auto High Cube Placed: " + cursor.getString(cursor.getColumnIndex("autoHighCubePlaced")));
        autoLowTextView.setText("Auto Low Cube Placed: " + cursor.getString(cursor.getColumnIndex("autoLowCubePlaced")));
        highCubesTextView.setText("High Cubes Placed: " + cursor.getString(cursor.getColumnIndex("highCubesPlaced")));
        lowCubesTextView.setText("Low Cubes Placed: " + cursor.getString(cursor.getColumnIndex("lowCubesPlaced")));
        vaultCubesTextView.setText("Vault Cubes Placed: " + cursor.getString(cursor.getColumnIndex("vaultCubesPlaced")));
        robotClimbTextView.setText("Climb Time: " + cursor.getString(cursor.getColumnIndex("climbTime")));
        successTextView.setText("Successful Climb: " + cursor.getString(cursor.getColumnIndex("climbSuccess")));
        robotNotesTextView.setText("Notes: " + cursor.getString(cursor.getColumnIndex("robotNotes")));
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // R.layout.list_row is your xml layout for each row
        cursorInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = cursorInflater.inflate(R.layout.data_list, parent, false);
        bindView(v, context, cursor);
        return v;
    }
}
