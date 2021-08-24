package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class    MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate()");
    }

    public void goToSecondActivity(View view) {

        EditText nameEditText = findViewById(R.id.nameEditText);
        EditText ageEditText = findViewById(R.id.ageEditText);
        EditText bioEditText = findViewById(R.id.bioEditText);
        EditText jobEditText = findViewById((R.id.jobEditText));
        submitButton = findViewById(R.id.submitButton);

        if (nameEditText.getText().toString().trim().isEmpty()) {
            nameEditText.setError("Please enter name!");
            return;
        }
        if (ageEditText.getText().toString().trim().isEmpty()) {
            ageEditText.setError("Please enter age!");
            return;
        }
        if (jobEditText.getText().toString().trim().isEmpty()) {
            jobEditText.setError("Please enter occupation!");
            return;
        }
        if (bioEditText.getText().toString().trim().isEmpty()) {
            bioEditText.setError("Please enter description!");
            return;
        }

        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra(Constants.KEY_NAME, nameEditText.getText().toString());
        intent.putExtra(Constants.KEY_AGE, ageEditText.getText().toString());
        intent.putExtra(Constants.KEY_JOB, jobEditText.getText().toString());
        intent.putExtra(Constants.KEY_BIO, bioEditText.getText().toString());
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
        Log.i(TAG, "onRestart()");
    }

    @Override
    protected  void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.i(TAG, "onSaveInstanceState()");
        outState.putString(Constants.KEY_BUTTON_TXT, submitButton.getText().toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }
}