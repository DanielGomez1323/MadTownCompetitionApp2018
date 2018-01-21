package com.example.pollo.madtowncompetitionapp2018;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;


/**
 */
public class TeleopFragment extends Fragment {
    Button decreaseHighCubePlacedButton;
    TextView amountHighCubesPlacedTextView;
    Button increaseHighCubePlacedButton;
    int highCubesPlaced = 0;
    Button decreaseLowCubePlacedButton;
    TextView amountLowCubesPlacedTextView;
    Button increaseLowCubePlacedButton;
    int lowCubesPlaced = 0;
    Button decreaseVaultCubePlacedButton;
    TextView amountVaultCubesPlacedTextView;
    Button increaseVaultCubePlacedButton;
    int vaultCubesPlaced = 0;
    SeekBar climbTimeSeekBar;
    TextView climbCountTextView;
    int climbTime = 0;
    CheckBox successfulClimbCheckBox;




    public static TeleopFragment newInstance() {
        TeleopFragment fragment = new TeleopFragment();
        return fragment;
    }


    public TeleopFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.teleop_period_fragment, container, false);
        decreaseHighCubePlacedButton = rootView.findViewById(R.id.decreaseHighCubePlacedButton);
        amountHighCubesPlacedTextView = rootView.findViewById(R.id.amountHighCubesPlacedTextView);
        increaseHighCubePlacedButton = rootView.findViewById(R.id.increaseHighCubePlacedButton);
        decreaseLowCubePlacedButton = rootView.findViewById(R.id.decreaseLowCubePlacedButton);
        amountLowCubesPlacedTextView = rootView.findViewById(R.id.amountLowCubesPlacedTextView);
        increaseLowCubePlacedButton = rootView.findViewById(R.id.increaseLowCubePlacedCubeButton);
        decreaseVaultCubePlacedButton = rootView.findViewById(R.id.decreaseVaultCubePlacedButton);
        amountVaultCubesPlacedTextView = rootView.findViewById(R.id.amountVaultCubesPlacedTextView);
        increaseVaultCubePlacedButton = rootView.findViewById(R.id.increaseVaultCubePlacedButton);
        climbTimeSeekBar = rootView.findViewById(R.id.climbTimeSeekBar);
        climbCountTextView = rootView.findViewById(R.id.climbCountTextView);
        successfulClimbCheckBox = rootView.findViewById(R.id.successfulClimbCheckBox);
        decreaseHighCubePlacedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highCubesPlaced --;
                amountHighCubesPlacedTextView.setText(String.valueOf(highCubesPlaced));
            }
        });
        increaseHighCubePlacedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highCubesPlaced ++;
                amountHighCubesPlacedTextView.setText(String.valueOf(highCubesPlaced));
            }
        });
        decreaseLowCubePlacedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lowCubesPlaced --;
                amountLowCubesPlacedTextView.setText(String.valueOf(lowCubesPlaced));
            }
        });
        increaseLowCubePlacedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lowCubesPlaced ++;
                amountLowCubesPlacedTextView.setText(String.valueOf(lowCubesPlaced));
            }
        });
        decreaseVaultCubePlacedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaultCubesPlaced --;
                amountVaultCubesPlacedTextView.setText(String.valueOf(vaultCubesPlaced));
            }
        });
        increaseVaultCubePlacedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaultCubesPlaced ++;
                amountVaultCubesPlacedTextView.setText(String.valueOf(vaultCubesPlaced));
            }
        });
        climbTimeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                climbCountTextView.setText("ClimbTime: " + progress);
                climbTime = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return rootView;
    }
    public Bundle getData() {
        Bundle b = new Bundle();
        b.putString("highCubesPlaced", String.valueOf(highCubesPlaced));
        b.putString("lowCubesPlaced", String.valueOf(lowCubesPlaced));
        b.putString("vaultCubesPlaced", String.valueOf(vaultCubesPlaced));
        b.putString("climbTime", String.valueOf(climbTime));
        if (successfulClimbCheckBox.isChecked()){
            b.putString("climbSuccess", "1");
        }else{
            b.putString("climbSuccess", "0");
        }
        return b;
    }
}
