package com.example.pollo.madtowncompetitionapp2018;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.CheckBox;


public class AutoFragment extends Fragment {
    CheckBox leftPositionCheckBox;
    CheckBox centerPositionCheckBox;
    CheckBox rightPositionCheckBox;
    CheckBox baselineCrossedCheckBox;
    CheckBox highCubeCheckBox;
    CheckBox lowCubeCheckBox;


    public static AutoFragment newInstance() {
        AutoFragment fragment = new AutoFragment();
        return fragment;
    }

    public AutoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.auto_period_fragment, container, false);
        leftPositionCheckBox = rootView.findViewById(R.id.leftPositionCheckBox);
        centerPositionCheckBox = rootView.findViewById(R.id.centerPositionCheckBox);
        rightPositionCheckBox = rootView.findViewById(R.id.rightPositionCheckBox);
        baselineCrossedCheckBox = rootView.findViewById(R.id.baselineCrossedCheckBox);
        highCubeCheckBox = rootView.findViewById(R.id.highCubeCheckBox);
        lowCubeCheckBox = rootView.findViewById(R.id.lowCubeCheckBox);

        leftPositionCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (leftPositionCheckBox.isChecked()){
                    centerPositionCheckBox.setChecked(false);
                    rightPositionCheckBox.setChecked(false);
                }

            }
        });
        centerPositionCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (centerPositionCheckBox.isChecked()){
                    leftPositionCheckBox.setChecked(false);
                    rightPositionCheckBox.setChecked(false);
                }
            }
        });
        rightPositionCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rightPositionCheckBox.isChecked()){
                    leftPositionCheckBox.setChecked(false);
                    centerPositionCheckBox.setChecked(false);
                }
            }
        });
        return rootView;
    }


}
