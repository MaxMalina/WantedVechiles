package com.dogecorporation.learn.findthat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import model.MyRequest;
import okhttp3.OkHttpClient;

public class SearchActivity extends AppCompatActivity {

    private TextView mNomLabel;

    private EditText mNom;
    private EditText mNSH;

    private Button mFind;

    private MyRequest myRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mNomLabel = findViewById(R.id.nomLabel);

        mNom = findViewById(R.id.nom);
        mNSH = findViewById(R.id.nsh);

        mFind = findViewById(R.id.find);

        mFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRequest = new MyRequest();
                try {
                    myRequest.execute(mNom.getText().toString(), mNSH.getText().toString());
                    Thread.sleep(1000);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(SearchActivity.this, ResultActivity.class);
                    intent.putExtra("data", myRequest.getResult());
                    startActivity(intent);
                }
            }
        });

        mNomLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),myRequest.getResult().toString(), Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}
