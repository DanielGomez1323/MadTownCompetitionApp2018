package com.example.pollo.madtowncompetitionapp2018;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class NotesFragment extends Fragment {
    EditText robotNotesEditText;

    public static NotesFragment newInstance() {
        NotesFragment fragment = new NotesFragment();
        return fragment;
    }

    public NotesFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notes, container, false);
        robotNotesEditText = rootView.findViewById(R.id.robotNotesEditText);
        return rootView;
    }
    public Bundle getData(){
        Bundle b = new Bundle();
        b.putString("robotNotes", robotNotesEditText.getText().toString());
        return b;
    }

}
