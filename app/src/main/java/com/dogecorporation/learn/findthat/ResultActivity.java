package com.dogecorporation.learn.findthat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private TextView mLocation;
    private TextView mNom;
    private TextView mModel;
    private TextView mNsh;
    private TextView mNcu;
    private TextView mColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mLocation = findViewById(R.id.location);
        mNom = findViewById(R.id.nom);
        mModel = findViewById(R.id.model);
        mNsh = findViewById(R.id.nsh);
        mNcu = findViewById(R.id.ncu);
        mColor = findViewById(R.id.color);

        ArrayList<String> dataToShow = getIntent().getExtras().getStringArrayList("data");
        mLocation.setText(dataToShow.get(0));
        mNom.setText(dataToShow.get(1));
        mModel.setText(dataToShow.get(2));
        mNsh.setText(dataToShow.get(3));
        mNcu.setText(dataToShow.get(4));
        mColor.setText(dataToShow.get(5));

    }
}
