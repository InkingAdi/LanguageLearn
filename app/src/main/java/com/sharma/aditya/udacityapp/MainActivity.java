package com.sharma.aditya.udacityapp;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    /**
     * OnClickListener
     */
    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.flColors:
                    Log.i(TAG, "onClick: Colors Called");
                    Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this)
                            .toBundle();
                    startActivity(new Intent(MainActivity.this, Colors.class),bundle);
                    break;
                case R.id.flFamily:
                    Log.i(TAG, "onClick: Family Called");
                    startActivity(new Intent(MainActivity.this, Family.class));
                    break;
                case R.id.flNumber:
                    Log.i(TAG, "onClick: Numbers Called");
                    startActivity(new Intent(MainActivity.this, Numbers.class));
                    break;
                case R.id.flPhrases:
                    Log.i(TAG, "onClick: Phrases Called");
                    startActivity(new Intent(MainActivity.this, Phrases.class));
                    break;
            }
        }
    };

    /**
     * Initialize Ui
     */
    private void InItUi() {
        findViewById(R.id.flColors).setOnClickListener(listener);
        findViewById(R.id.flFamily).setOnClickListener(listener);
        findViewById(R.id.flNumber).setOnClickListener(listener);
        findViewById(R.id.flPhrases).setOnClickListener(listener);
    }

    /**
     * On Create Method Called
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Ui
        InItUi();
    }
}