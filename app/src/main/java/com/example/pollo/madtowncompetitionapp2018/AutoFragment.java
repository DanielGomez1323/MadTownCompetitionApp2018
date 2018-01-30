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
        highCubeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lowCubeCheckBox.isChecked()){
                    lowCubeCheckBox.toggle();
                }
            }
        });
        lowCubeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (highCubeCheckBox.isChecked()){
                    highCubeCheckBox.toggle();
                }
            }
        });
        return rootView;
    }
    public Bundle getData() {
        Bundle b = new Bundle();
        if (leftPositionCheckBox.isChecked()) {
            b.putString("robotPosition", "0");
        }else if (centerPositionCheckBox.isChecked()){
            b.putString("robotPosition", "1");
        }else if (rightPositionCheckBox.isChecked()){
            b.putString("robotPosition", "2");
        }else {
            b.putString("robotPosition", "3");
        }
        if (baselineCrossedCheckBox.isChecked()){
            b.putString("baseLineCrossed", "1");
        }else{
            b.putString("baseLineCrossed", "0");
        }
        if (highCubeCheckBox.isChecked()){
            b.putString("autoHighCubePlaced", "1");
        }else {
            b.putString("autoHighCubePlaced", "0");
        }
        if (lowCubeCheckBox.isChecked()){
            b.putString("autoLowCubePlaced", "1");
        }else {
            b.putString("autoLowCubePlaced", "0");
        }
        return b;
    }
}
